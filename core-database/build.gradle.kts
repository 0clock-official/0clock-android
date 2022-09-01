import com.xyz.oclock.Configuration

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
}

android {
    compileSdk = Configuration.compileSdk

    defaultConfig {
        minSdk = Configuration.minSdk
        targetSdk = Configuration.targetSdk
    }
}

dependencies {
    implementation(project(":core-model"))

    // coroutines
    implementation(libs.coroutines)
    testImplementation(libs.coroutines)
    testImplementation(libs.coroutines.test)

    // di
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // logger
    api(libs.timber)

    implementation(libs.androidx.crypto.ktx)
}