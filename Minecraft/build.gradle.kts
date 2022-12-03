plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.papermc.paperweight.userdev") version "1.3.6"
}


group = "io.github.rysefoxx.example"
version = "1.0-SNAPSHOT"
java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

repositories {
    mavenCentral()
    maven { url = uri("https://s01.oss.sonatype.org/content/groups/public/") }
    maven("https://repo.codemc.io/repository/maven-snapshots/")
}

dependencies {
    paperDevBundle("1.19-R0.1-SNAPSHOT")
    implementation("io.github.rysefoxx.inventory:RyseInventory-Plugin:1.5.4")

    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
}

tasks {
    shadowJar {
        mergeServiceFiles()
    }
}