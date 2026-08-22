package com.embabel.learning.kotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Bootstraps the Kotlin Embabel learning shell.
 *
 * Embabel 1.5 auto-configures from the starter — `@EnableAgents` is deprecated/unnecessary.
 * Cookbook 1.5 counterparts live in `cookbook15`.
 *
 * Study entry: `docs/WALKTHROUGH.md` and [com.embabel.learning.common.curriculum.LessonOrder].
 *
 * Run: `./mvnw -pl kotlin-demo spring-boot:run`
 */
@SpringBootApplication(scanBasePackages = ["com.embabel.learning.kotlin", "com.embabel.learning.common"])
class KotlinLearningApplication

fun main(args: Array<String>) {
    runApplication<KotlinLearningApplication>(*args)
}
