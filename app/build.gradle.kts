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
    testImplementation("org.bitcoindevkit:bdk-jvm:0.8.0-SNAPSHOT")

    // Use the Kotlin test library.
    testImplementation(kotlin("test"))

    // testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.8.2")
    // testImplementation(platform("org.junit:junit-bom:5.8.2"))
    // testImplementation("org.junit.jupiter:junit-jupiter")
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
