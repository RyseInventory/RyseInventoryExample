plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "io.github.rysefoxx.example"
version = "1.0-SNAPSHOT"
description = "This build.gradle.kts shows a possible structure for RyseInventory. Yours will differ for sure!"

repositories {
    mavenCentral()
    maven { url = uri("https://s01.oss.sonatype.org/content/groups/public/") }
}

dependencies {
    implementation("io.github.rysefoxx.inventory:RyseInventory-Plugin:1.5.4")
}

tasks {
    shadowJar {
        mergeServiceFiles()
    }
}