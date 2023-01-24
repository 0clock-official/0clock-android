import com.xyz.oclock.Configuration

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.android.application.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    id(libs.plugins.navigation.safeargs.kotlin.get().pluginId)
    id(libs.plugins.hilt.plugin.get().pluginId)
    id(libs.plugins.gms.google.service.get().pluginId)
    id(libs.plugins.jetbrains.kotlin.android.get().pluginId) //
}

android {
    compileSdk = Configuration.compileSdk

    defaultConfig {
        applicationId = "com.xyz.oclock"
        minSdk = Configuration.minSdk
        targetSdk = Configuration.targetSdk
        versionCode = Configuration.versionCode
        versionName = Configuration.versionName
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        dataBinding = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // modules
    implementation(project(":core-data"))

    // androidx
    implementation(libs.material)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.viewpager)

    // whatIf
    implementation(libs.whatif)

    // recyclerView
    implementation(libs.recyclerview)

    // fcm
    implementation(libs.gms.play.service.framework)

    // unit test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)

    // navigation
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.dynamic.features)
    implementation(libs.androidx.navigation.testing)
    implementation(libs.androidx.navigation.compose)

    // glide
    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)

    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.bindables)

    implementation(libs.firebase.messaging)

    implementation(libs.pickimage)

    implementation(libs.androidx.crypto.ktx)
}