package com.embabel.learning.common.curriculum;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CookbookVideoBundleTest {

    @Test
    void narrationFilesAndIngestManifestLineUp() throws Exception {
        Path root = findRepoRoot();
        for (CookbookChapter chapter : CookbookChapter.values()) {
            Path narration = root.resolve(chapter.narrationFile());
            assertTrue(Files.isRegularFile(narration), "missing " + narration);
            assertTrue(Files.size(narration) > 80, narration + " looks empty");
        }
        assertTrue(Files.isRegularFile(root.resolve("docs/demos/narration/00-overview.md")));
        assertTrue(Files.isRegularFile(root.resolve("docs/videos/memory-os/embabel-cheatsheet.md")));
        assertTrue(Files.isRegularFile(root.resolve("docs/videos/memory-os/embabel-cheatsheet.palace.yaml")));
        assertTrue(Files.isRegularFile(root.resolve("docs/CHEATSHEET.md")));
        assertTrue(Files.isRegularFile(root.resolve("docs/index.html")));
        assertTrue(Files.isRegularFile(root.resolve(".github/workflows/pages.yml")));

        JsonNode manifest = new ObjectMapper().readTree(
                root.resolve("docs/videos/memory-os/ingest-manifest.json").toFile()
        );
        assertEquals("cheatsheet", manifest.get("track").asText());
        assertEquals("embabel-cheatsheet", manifest.get("palace").get("id").asText());
        assertEquals(12, manifest.get("segments").size());
        assertEquals(
                CookbookChapterCatalog.MEMORY_OS_REPO,
                manifest.get("memory_os_repo").asText()
        );
        assertEquals(
                CookbookChapterCatalog.MEMORY_OS_SHA,
                manifest.get("memory_os_sha").asText()
        );
        String installScript = Files.readString(root.resolve("scripts/memoryos-cloud-install.sh"));
        assertTrue(
                installScript.contains("@8822fcb"),
                "memoryos-cloud-install.sh must pin memory-os @8822fcb"
        );
    }

    @Test
    void installScriptAssertsMemoryOsShaNotVersionString() throws Exception {
        Path root = findRepoRoot();
        Path helper = root.resolve("scripts/memoryos-assert-pin.sh");
        String installScript = Files.readString(root.resolve("scripts/memoryos-cloud-install.sh"));
        String helperScript = Files.readString(helper);

        assertTrue(
                installScript.contains("@8822fcb"),
                "memoryos-cloud-install.sh must pin memory-os @8822fcb"
        );
        assertTrue(
                installScript.contains("memoryos-assert-pin.sh"),
                "install must verify the recorded SHA after pip, not only print --version"
        );
        assertTrue(
                helperScript.contains("8822fcb5ae813f491daeff7d5f37fce7ff10d213")
                        || helperScript.contains("8822fcb"),
                "assert helper must pin 8822fcb"
        );
        assertTrue(
                helperScript.contains("direct_url") && helperScript.contains("commit_id"),
                "assert helper must read PEP 610 commit_id, not __version__"
        );

        Path good = Files.createTempFile("memoryos-direct-url-good", ".json");
        Path drifted = Files.createTempFile("memoryos-direct-url-drift", ".json");
        Path versionOnly = Files.createTempFile("memoryos-direct-url-version", ".json");
        Files.writeString(good, """
                {"url":"https://github.com/jmjava/memory-os.git","vcs_info":{"vcs":"git","commit_id":"8822fcb5ae813f491daeff7d5f37fce7ff10d213","requested_revision":"8822fcb"}}
                """);
        Files.writeString(drifted, """
                {"url":"https://github.com/jmjava/memory-os.git","vcs_info":{"vcs":"git","commit_id":"2e94f8d000000000000000000000000000000000","requested_revision":"2e94f8d"}}
                """);
        Files.writeString(versionOnly, """
                {"url":"https://github.com/jmjava/memory-os.git","version":"0.1.0"}
                """);

        assertEquals(0, runAssertPin(helper, good), "pin 8822fcb must pass");
        assertNotEquals(0, runAssertPin(helper, drifted), "2e94f8d must fail even though version is 0.1.0");
        assertNotEquals(0, runAssertPin(helper, versionOnly), "version 0.1.0 without a SHA must fail");
    }

    @Test
    void pagesYmlValidatesPalaceFrontMatterBeforeDeploy() throws Exception {
        Path root = findRepoRoot();
        String pages = Files.readString(root.resolve(".github/workflows/pages.yml"));
        assertTrue(
                pages.contains("scripts/validate-pages-docs.sh"),
                "pages.yml must run scripts/validate-pages-docs.sh before deploy"
        );
        assertTrue(
                !pages.contains("continue-on-error"),
                "pages.yml must not continue-on-error; validate must be able to go red"
        );
        int validateAt = pages.indexOf("validate-pages-docs.sh");
        int uploadAt = pages.indexOf("upload-pages-artifact");
        int deployAt = pages.indexOf("deploy-pages@");
        assertTrue(validateAt >= 0, "missing validate-pages-docs.sh in pages.yml");
        assertTrue(uploadAt > validateAt, "validate must run before upload-pages-artifact");
        assertTrue(deployAt > uploadAt, "upload must run before deploy-pages");
        assertEquals(0, runPagesValidate(root), "current palace front matter must pass Pages validate");
    }

    @Test
    void brokenPalaceFrontMatterFailsPagesValidate() throws Exception {
        Path root = findRepoRoot();
        Path tmp = Files.createTempDirectory("pages-validate-broken-");
        Path destPalace = tmp.resolve("docs/videos/memory-os");
        Files.createDirectories(destPalace);
        Path srcPalace = root.resolve("docs/videos/memory-os");
        try (Stream<Path> walk = Files.walk(srcPalace)) {
            for (Path src : walk.filter(path -> path.toString().endsWith(".md")).toList()) {
                Path rel = srcPalace.relativize(src);
                Path out = destPalace.resolve(rel);
                Files.createDirectories(out.getParent());
                Files.copy(src, out, StandardCopyOption.REPLACE_EXISTING);
            }
        }
        Path cheatsheet = destPalace.resolve("embabel-cheatsheet.md");
        String original = Files.readString(cheatsheet);
        assertTrue(original.contains("**Purpose:**"), "fixture must start with a Purpose label");
        Files.writeString(cheatsheet, original.replace("**Purpose:**", "**Porpoise:**"));

        int broken = runPagesValidate(tmp);
        assertNotEquals(0, broken, "broken palace front matter must fail the Pages deploy validate");
        assertEquals(0, runPagesValidate(root), "unbroken repo docs must still pass");
    }

    private static int runPagesValidate(Path repoRoot) throws Exception {
        Path helper = findRepoRoot().resolve("scripts/validate-pages-docs.sh");
        ProcessBuilder pb = new ProcessBuilder("bash", helper.toString(), repoRoot.toString());
        pb.redirectErrorStream(true);
        Process process = pb.start();
        String output = new String(process.getInputStream().readAllBytes());
        boolean finished = process.waitFor(20, TimeUnit.SECONDS);
        assertTrue(finished, "validate-pages-docs.sh timed out\n" + output);
        return process.exitValue();
    }

    private static int runAssertPin(Path helper, Path directUrlJson) throws Exception {
        ProcessBuilder pb = new ProcessBuilder("bash", helper.toString());
        pb.environment().put("MEMORYOS_DIRECT_URL_JSON", directUrlJson.toString());
        pb.redirectErrorStream(true);
        Process process = pb.start();
        String output = new String(process.getInputStream().readAllBytes());
        boolean finished = process.waitFor(20, TimeUnit.SECONDS);
        assertTrue(finished, "memoryos-assert-pin.sh timed out\n" + output);
        return process.exitValue();
    }

    private static Path findRepoRoot() {
        Path dir = Path.of("").toAbsolutePath();
        for (int i = 0; i < 6; i++) {
            if (Files.isRegularFile(dir.resolve("docs/videos/memory-os/ingest-manifest.json"))) {
                return dir;
            }
            dir = dir.getParent();
        }
        throw new IllegalStateException("Could not locate repo root from " + Path.of("").toAbsolutePath());
    }
}
