import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.time.Duration

plugins {
    kotlin("multiplatform") version "2.1.21"
    id("org.jetbrains.dokka") version "2.0.0"
    id("com.android.library") version "8.10.1"

    `maven-publish`
}

val v = "0.4.4"

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

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            testTask {
                useMocha {
                    timeout = "10m"
                }
            }
        }
        useCommonJs()
        generateTypeScriptDefinitions()
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
            compileOnly("com.soywiz.korge:korge-core:6.0.0")
        }

        listOf(nativeMain, wasmJsMain).forEach { sourceSet ->
            sourceSet.dependencies {
                api("com.soywiz.korge:korge-core:6.0.0")
            }
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")
            implementation("com.soywiz.korge:korge-core:6.0.0")
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