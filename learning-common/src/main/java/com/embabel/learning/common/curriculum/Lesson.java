package com.embabel.learning.common.curriculum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class as a numbered study lesson in the Embabel curriculum.
 * <p>
 * Generated Javadoc will surface these tags so you can navigate the guided walkthrough
 * by lesson number. Cross-link from Markdown docs under {@code docs/} using the package
 * and class names.
 *
 * @see LessonOrder
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.PACKAGE})
public @interface Lesson {

    /**
     * Curriculum order (1-based). Lower numbers should be studied first.
     */
    int value();

    /**
     * Short human-readable lesson title.
     */
    String title();

    /**
     * Official Embabel guide sections this lesson maps to.
     */
    String[] guideRefs() default {};

    /**
     * Sister type in the other language module, if any (fully qualified name).
     */
    String counterpart() default "";
}
