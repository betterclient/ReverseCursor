@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform") version "2.1.21"
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.21"
    id("org.jetbrains.compose") version "1.8.1"
}

kotlin {
    js(IR) {
        moduleName = "reverse-cursor-kotlin-clientside"
        browser {
            commonWebpackConfig {
                outputFileName = "koutrev.js"
            }

            testTask {
                enabled = false
            }
        }
        binaries.executable()
    }

    sourceSets {
        jsMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)

            //speed up build, check out "webpack.config.d/config.js"
            implementation(npm("esbuild", "0.20.0"))
            implementation(npm("esbuild-loader", "4.0.0"))
        }
    }
}

dependencies {

}