import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("multiplatform") version "2.0.0"
    id("org.jetbrains.dokka") version "1.9.20"
    id("com.android.library") version "8.5.1"

    `maven-publish`
}

val v = "0.3.5"

group = "xyz.calcugames.combinatory"
version = if (project.hasProperty("snapshot")) "$v-SNAPSHOT" else v
description = "Open-Source code for the Combinatory Game"

repositories {
    google()
    mavenCentral()
    mavenLocal()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

kotlin {
    jvm {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }

    iosX64()
    iosSimulatorArm64()
    iosArm64()
    tvosX64()
    tvosSimulatorArm64()
    tvosArm64()
    androidTarget {
        publishAllLibraryVariants()
    }

    sourceSets {
        commonMain.dependencies {
            compileOnly("com.soywiz.korge:korge-core:5.4.0")
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation("com.soywiz.korge:korge-core:5.4.0")
        }
    }
}

android {
    compileSdk = 33
    namespace = "xyz.calcugames.combinatory.calculatory"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

tasks {
    clean {
        delete("kotlin-js-store")
    }

    withType<Test>().configureEach {
        useJUnitPlatform()
        testLogging {
            showStandardStreams = true
            events("passed", "skipped", "failed")
        }
    }
}

publishing {
    publications {
        filterIsInstance<MavenPublication>().forEach { it.apply {
            pom {
                name = "calculatory"
                description = "Algorithms used in the Combinatory Video Game"

                licenses {
                    license {
                        name = "MIT License"
                        url = "https://opensource.org/licenses/MIT"
                    }
                }

                scm {
                    connection = "scm:git:git://github.com/CalculusGames/calculatory.git"
                    developerConnection = "scm:git:ssh://github.com/CalculusGames/calculatory.git"
                    url = "https://github.com/CalculusGames/calculatory"
                }
            }
        }}
    }

    repositories {
        maven {
            credentials {
                username = System.getenv("NEXUS_USERNAME")
                password = System.getenv("NEXUS_PASSWORD")
            }

            val releases = "https://repo.calcugames.xyz/repository/maven-releases/"
            val snapshots = "https://repo.calcugames.xyz/repository/maven-snapshots/"
            url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshots else releases)
        }
    }
}