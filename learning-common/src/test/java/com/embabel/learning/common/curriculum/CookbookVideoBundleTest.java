package com.embabel.learning.common.curriculum;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        JsonNode manifest = new ObjectMapper().readTree(
                root.resolve("docs/videos/memory-os/ingest-manifest.json").toFile()
        );
        assertEquals("1.5.0", manifest.get("embabel_version").asText());
        assertEquals(13, manifest.get("segments").size());
        assertEquals(
                CookbookChapterCatalog.MEMORY_OS_REPO,
                manifest.get("memory_os_repo").asText()
        );
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
