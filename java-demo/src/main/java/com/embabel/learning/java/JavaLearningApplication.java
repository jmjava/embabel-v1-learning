package com.embabel.learning.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Bootstraps the Java Embabel learning shell.
 * <p>
 * Embabel 1.0 auto-configures from the starter — {@code @EnableAgents} is no longer required
 * (and is deprecated).
 * <p>
 * Study entry points:
 * <ul>
 *   <li>{@code docs/WALKTHROUGH.md}</li>
 *   <li>{@link com.embabel.learning.common.curriculum.LessonOrder}</li>
 *   <li>package-info in each {@code lessonNN} package</li>
 * </ul>
 *
 * <p>Run: {@code ./mvnw -pl java-demo spring-boot:run}
 * (set {@code OPENAI_API_KEY} or {@code ANTHROPIC_API_KEY} for live LLM calls).
 */
@SpringBootApplication(scanBasePackages = {
        "com.embabel.learning.java",
        "com.embabel.learning.common"
})
public class JavaLearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaLearningApplication.class, args);
    }
}
