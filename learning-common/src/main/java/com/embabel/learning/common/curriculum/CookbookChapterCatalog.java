package com.embabel.learning.common.curriculum;

import java.util.Arrays;
import java.util.List;

/**
 * Stable catalog of official Cookbook 1.5 chapters for docs, tests, and video ingest.
 */
public final class CookbookChapterCatalog {

    public static final String COOKBOOK_REPO = "https://github.com/embabel/embabel-cookbook";
    public static final String COOKBOOK_DOCS = "https://docs.embabel.com/embabel-cookbook/1.5.0/";
    public static final String COOKBOOK_VERSION = "1.5.0";
    public static final String MEMORY_OS_REPO = "https://github.com/jmjava/memory-os";
    public static final String DOCGEN_REPO = "https://github.com/jmjava/documentation-generator";

    private CookbookChapterCatalog() {
    }

    public static List<CookbookChapter> all() {
        return Arrays.asList(CookbookChapter.values());
    }

    public static List<CookbookChapter> partOne() {
        return all().stream().filter(chapter -> chapter.part().startsWith("Part 1")).toList();
    }

    public static List<CookbookChapter> partTwo() {
        return all().stream().filter(chapter -> chapter.part().startsWith("Part 2")).toList();
    }
}
