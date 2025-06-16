import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("multiplatform") version "2.1.21"
}

group = "io.github.betterclient"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
        google()
    }
}

kotlin {
    jvm { compilerOptions { jvmTarget = JvmTarget.JVM_21 } }
    js(IR) { browser() }
}