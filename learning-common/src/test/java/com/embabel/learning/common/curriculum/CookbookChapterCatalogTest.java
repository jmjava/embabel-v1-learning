package com.embabel.learning.common.curriculum;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CookbookChapterCatalogTest {

    @Test
    void officialCookbookHasTwelveChapters() {
        assertEquals(12, CookbookChapterCatalog.all().size());
        assertEquals(6, CookbookChapterCatalog.partOne().size());
        assertEquals(6, CookbookChapterCatalog.partTwo().size());
    }

    @Test
    void slugsMatchOfficialManuscriptNames() {
        Set<String> slugs = Arrays.stream(CookbookChapter.values())
                .map(CookbookChapter::slug)
                .collect(Collectors.toSet());
        assertTrue(slugs.contains("thinking"));
        assertTrue(slugs.contains("streaming"));
        assertTrue(slugs.contains("create-object-if-possible"));
        assertTrue(slugs.contains("action-heuristics"));
    }

    @Test
    void eachChapterPointsAtALessonAndNarrationFile() {
        for (CookbookChapter chapter : CookbookChapter.values()) {
            assertTrue(chapter.lessonNumber() >= 1);
            assertTrue(chapter.officialUrl().contains(chapter.slug()));
            assertTrue(chapter.narrationFile().contains(chapter.slug()));
            assertEquals("1.5.0", CookbookChapterCatalog.COOKBOOK_VERSION);
        }
    }
}
