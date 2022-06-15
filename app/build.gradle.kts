import org.gradle.api.tasks.testing.logging.TestExceptionFormat.*
import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.0"
    application
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    // bitcoindevkit
    testImplementation("org.bitcoindevkit:bdk-jvm:0.7.0-SNAPSHOT")

    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.8.2")
}

tasks.withType<Test> {
    useJUnitPlatform()

    testLogging {
        events(PASSED, SKIPPED, FAILED, STANDARD_OUT, STANDARD_ERROR)
        exceptionFormat = FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true
    }
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useKotlinTest()
        }
    }
}

application {
    mainClass.set("bdk.jvm.tests.AppKt")
}
