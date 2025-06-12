plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.sqldelight)
}

kotlin {
    applyDefaultHierarchyTemplate()

    sourceSets.all {
        languageSettings.apply {
            optIn("kotlin.ExperimentalMultiplatform")
            optIn("kotlin.time.ExperimentalTime")
            optIn("kotlin.RequiresOptIn")
            progressiveMode = true
            languageVersion = "2.1"
        }
    }

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = libs.versions.jvmTarget.get()
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    )
        // Export the framework only for Xcode builds
        .takeIf { "XCODE_VERSION_MAJOR" in System.getenv().keys }
        //noinspection WrongGradleMethod
        ?.forEach {
            // This `shared` framework is exported for app-ios-swift
            it.binaries.framework {
                // Used in app-ios-swift
                baseName = "shared"
                export(compose.foundation)
                export(compose.material3)
                export(compose.ui)
                export(libs.decompose.decompose)
                export(libs.decompose.extensionsComposeJetbrains)
                export(libs.essenty.lifecycle)
                export(libs.koin.core)
                export(libs.material.kolor)
            }
        }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.ui)
                api(compose.foundation)
                api(compose.material3)
                api(libs.essenty.lifecycle)
                api(libs.decompose.decompose)
                api(libs.decompose.extensionsComposeJetbrains)
                api(libs.koin.core)
                api(libs.material.kolor)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.compose.multiplatform.calendar)
                implementation(libs.kermit)
                implementation(libs.kotlinx.datetime)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.sqldelight.coroutines.extensions)
                implementation(libs.sqldelight.runtime)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.koin.test)
                implementation(libs.kotlinx.coroutines.test)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.sqldelight.android.driver)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(libs.sqldelight.native.driver)
            }
        }
    }
}

android {
    namespace = "com.saikcaskey.github.pokertracker.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

sqldelight {
    databases {
        create("PokerTrackerDatabase") {
            packageName.set("com.saikcaskey.github.pokertracker.shared.database")
        }
    }
}
