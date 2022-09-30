import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.0"
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    // working with bytes
    testImplementation("com.google.guava:guava:31.1-jre")

    // bitcoindevkit
    testImplementation("org.bitcoindevkit:bdk-jvm:0.10.0-SNAPSHOT")

    // Use the Kotlin test library
    testImplementation(kotlin("test"))
}

tasks.withType<Test> {
    useJUnitPlatform()

    testLogging {
        events = setOf(
            TestLogEvent.PASSED,
            TestLogEvent.SKIPPED,
            TestLogEvent.FAILED,
            TestLogEvent.STANDARD_OUT,
            TestLogEvent.STANDARD_ERROR
        )
        exceptionFormat = TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true
    }
}
