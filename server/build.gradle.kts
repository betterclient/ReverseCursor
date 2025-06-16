import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation

plugins {
    kotlin("multiplatform") version "2.1.21"
}

dependencies { }

kotlin {
    jvm {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        binaries {
            executable(KotlinCompilation.MAIN_COMPILATION_NAME) {
                mainClass.set("io.github.betterclient.reversecursor.server.MainKt") //exists
            }
        }

        compilerOptions {
            jvmTarget = JvmTarget.JVM_21
        }
    }

    sourceSets {
        jvmMain.dependencies {
            implementation("org.slf4j:slf4j-simple:2.0.17")
            implementation("io.ktor:ktor-server-core:3.1.3")
            implementation("io.ktor:ktor-server-netty:3.1.3")
        }
    }
}

tasks.register<Copy>("copyFrontend") {
    dependsOn(":client:jsBrowserDistribution")
    from(project(":client").layout.buildDirectory.file("dist/js/productionExecutable"))
    into("src/jvmMain/resources/static")
}

tasks.named("jvmProcessResources") {
    dependsOn("copyFrontend")
}

afterEvaluate {
    tasks.named("runJvm") {
        dependsOn(":client:build")
    }
}