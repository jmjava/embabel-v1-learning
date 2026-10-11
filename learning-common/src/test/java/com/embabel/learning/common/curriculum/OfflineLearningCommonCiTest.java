package com.embabel.learning.common.curriculum;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OfflineLearningCommonCiTest {

    @Test
    void ciRunsOfflineLearningCommonTests() throws Exception {
        Path root = findRepoRoot();
        String ci = Files.readString(root.resolve(".github/workflows/ci.yml"));
        assertTrue(
                ci.contains("-pl learning-common"),
                "offline curriculum checks stay in the learning-common module"
        );
        assertTrue(ci.contains("CookbookChapterCatalogTest"), "CI must run CookbookChapterCatalogTest");
        assertTrue(ci.contains("LessonCoverageTest"), "CI must run LessonCoverageTest");
        assertTrue(ci.contains("CookbookVideoBundleTest"), "CI must run CookbookVideoBundleTest");
        assertFalse(ci.contains("continue-on-error"), "offline tests must be able to go red");
        assertFalse(ci.contains("GuidedTest"), "java-demo GuidedTest classes stay out of this job");
    }

    private static Path findRepoRoot() {
        Path dir = Path.of("").toAbsolutePath();
        Path candidate = dir;
        Path next = candidate.getParent();
        while (next != null && !Files.isRegularFile(candidate.resolve("docs/videos/memory-os/ingest-manifest.json"))) {
            candidate = next;
            next = candidate.getParent();
        }
        if (!Files.isRegularFile(candidate.resolve("docs/videos/memory-os/ingest-manifest.json"))) {
            throw new IllegalStateException("Could not locate repo root from " + dir);
        }
        return candidate;
    }
}
