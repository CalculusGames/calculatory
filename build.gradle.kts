plugins {
    kotlin("multiplatform") version "2.0.0"
    id("org.jetbrains.dokka") version "1.9.20"

    java
    `maven-publish`
    jacoco
}

val v = "0.2.1"

group = "xyz.calcugames.combinatory"
version = if (project.hasProperty("snapshot")) "$v-SNAPSHOT" else v
description = "Open-Source code for the Combinatory Game"

repositories {
    mavenCentral()
    mavenLocal()
}

kotlin {
    jvm()

    iosX64()
    iosArm64()

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
        finalizedBy(jacocoTestReport)
    }

    jacocoTestReport {
        dependsOn(check)

        reports {
            csv.required.set(false)

            xml.required.set(true)
            xml.outputLocation.set(layout.buildDirectory.file("jacoco.xml"))

            html.required.set(true)
            html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
        }
    }
}

publishing {
    publications {
        getByName<MavenPublication>("kotlinMultiplatform") {
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
        }
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