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
    // bitcoindevkit
    testImplementation("org.bitcoindevkit:bdk-jvm:0.7.0-SNAPSHOT")

    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.8.2")
    testImplementation(platform("org.junit:junit-bom:5.8.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // Kotest
    // testImplementation("io.kotest:kotest-runner-junit5:5.3.0")
    // testImplementation("io.kotest:kotest-assertions-core:5.3.0")
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

// Kotest
// tasks.withType<Test>().configureEach {
//     useJUnitPlatform()
// }

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useKotlinTest()
        }
    }
}
