import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.compose)
}

kotlin {
    applyDefaultHierarchyTemplate()

    targets.withType(KotlinNativeTarget::class).all {
        binaries.all {
            linkerOpts("-L/usr/lib", "-lsqlite3")
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
        iosSimulatorArm64()
    )
        .takeIf { "XCODE_VERSION_MAJOR" in System.getenv().keys }
        ?.forEach {
            it.binaries.framework {
                baseName = "shared"
                export(project(":shared"))
                export(libs.decompose.decompose)
                export(libs.decompose.extensionsComposeJetbrains)
                export(libs.essenty.lifecycle)
            }
        }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":shared"))
                api(libs.decompose.decompose)
                api(libs.decompose.extensionsComposeJetbrains)
                api(libs.essenty.lifecycle)

                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(libs.br.compose.icons.font.awesome)
                implementation(libs.compose.multiplatform.calendar)
                implementation(libs.kotlinx.datetime)
                implementation(libs.kermit)
            }
        }
    }
}

android {
    namespace = "com.saikcaskey.github.pokertracker.compose"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
