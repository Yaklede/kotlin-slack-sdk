plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("kapt") version "1.9.25"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

group = "io.github.Yaklede"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    //jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")

    //reflection
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    //junit
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    //assertj
    testImplementation("org.assertj:assertj-core:3.24.2")

    //config
    testImplementation("com.natpryce:konfig:1.6.10.0")
}

tasks.test {
    useJUnitPlatform()
}