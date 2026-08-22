package com.embabel.learning.common.curriculum;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Meta-test: documents the expected curriculum constants so gaps are obvious
 * when extending the demo.
 */
class LessonCoverageTest {

    @Test
    void lessonOrderConstantsAreContiguousFromOne() throws Exception {
        Set<Integer> values = new TreeSet<>();
        for (Field field : LessonOrder.class.getFields()) {
            if (field.getType() == int.class) {
                values.add(field.getInt(null));
            }
        }
        assertEquals(
                Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21),
                values,
                "Update LessonOrder + WALKTHROUGH.md together when adding lessons"
        );
        assertTrue(values.contains(LessonOrder.KOTLIN_DSL));
    }

    @Test
    void debugGuideAnnotationExposesBreakpointArray() {
        var methods = Arrays.stream(DebugGuide.class.getDeclaredMethods())
                .map(m -> m.getName())
                .collect(Collectors.toSet());
        assertTrue(methods.contains("breakpoints"));
        assertTrue(methods.contains("watch"));
        assertTrue(methods.contains("tip"));
    }
}
