package com.embabel.learning.common.curriculum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Documents recommended debugger breakpoints for a guided study test or method.
 * <p>
 * Appear in generated Javadoc. Prefer placing this on test methods that walk a key flow.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface DebugGuide {

    /**
     * Ordered breakpoint suggestions. Prefer fully-qualified locations:
     * {@code ClassName#methodName (reason)}.
     */
    String[] breakpoints();

    /**
     * What you should observe at those breakpoints.
     */
    String watch() default "";

    /**
     * Suggested IntelliJ run configuration tip.
     */
    String tip() default "Run the test in Debug mode (Shift+F9 / Ctrl+D).";
}
