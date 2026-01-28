import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

val localPropertiesFile = rootProject.file("local.properties")
val localProperties = Properties()
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

android {
    namespace = "com.ralphmarondev.lumi"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.ralphmarondev.lumi"
        minSdk = 28
        targetSdk = 36

        versionCode = localProperties["LUMI_VERSION_CODE"]?.toString()?.toInt() ?: 1
        versionName = localProperties["LUMI_VERSION_NAME"]?.toString() ?: "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file(localProperties["RELEASE_STORE_FILE"] ?: "lumi-release.jks")
            storePassword = localProperties["RELEASE_STORE_PASSWORD"]?.toString()
            keyAlias = localProperties["RELEASE_KEY_ALIAS"]?.toString()
            keyPassword = localProperties["RELEASE_KEY_PASSWORD"]?.toString()
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        }
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":system:boot"))
    implementation(project(":system:launcher"))
    implementation(project(":system:settings"))
    implementation(project(":system:media"))
    implementation(project(":miniapps:notes"))
    implementation(project(":miniapps:weather"))
    implementation(project(":miniapps:clock"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.material.icons.extended.android)
    implementation(libs.navigation.compose)
    implementation(libs.bundles.koin)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}