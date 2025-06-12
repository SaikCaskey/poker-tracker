import org.gradle.kotlin.dsl.android
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.compose)
}

android {
    namespace = "com.saikcaskey.github.pokertracker"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.saikcaskey.github.pokertracker.fdroid"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 2
        versionName = "0.1.1"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    // Load properties from local.properties
    val localProperties = Properties()
    val localPropertiesFile = project.rootProject.file("local.properties")

    if (localPropertiesFile.exists()) {
        FileInputStream(localPropertiesFile).use {
            localProperties.load(it)
        }
    } else {
        println("WARNING: local.properties file not found. App Signing won't work correctly.")
    }

    signingConfigs {
        create("fdroid") {
            keyAlias = localProperties.getProperty("POKERTRACKER_F_DROID_ALIAS")
            keyPassword = localProperties.getProperty("POKERTRACKER_F_DROID_PASSWORD")
            storeFile = file("../${localProperties.getProperty("POKERTRACKER_F_DROID_KEYSTORE")}")
            storePassword = localProperties.getProperty("POKERTRACKER_F_DROID_STORE_PASSWORD")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("fdroid")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":compose-ui"))
    implementation(libs.androidx.activity.activityCompose)
    implementation(libs.androidx.compose.material3)
}
