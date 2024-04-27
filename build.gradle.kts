import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform") version "1.9.23"
    id("org.jetbrains.dokka") version "1.9.20"

    java
    `maven-publish`
    jacoco
}

group = "xyz.calcugames.combinatory"
version = "0.1.0"
description = "Open-Source algorithms for the Combinatory Game"

repositories {
    mavenCentral()
    mavenLocal()
}

kotlin {
    jvm()

    js {
        browser()
    }

    sourceSets {
        jvmTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

tasks {
    clean {
        delete("kotlin-js-store")
    }

    named<Test>("jvmTest") {
        useJUnitPlatform()
        testLogging {
            showStandardStreams = true
            events("passed", "skipped", "failed")
        }
        finalizedBy(jacocoTestReport)
    }

    jacocoTestReport {
        dependsOn(test)

        reports {
            csv.required.set(false)

            xml.required.set(true)
            xml.outputLocation.set(layout.buildDirectory.file("jacoco.xml"))

            html.required.set(true)
            html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
        }
    }
}