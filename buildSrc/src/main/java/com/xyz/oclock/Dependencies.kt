package com.xyz.oclock

import com.xyz.oclock.Versions.APPCOMPAT_VERSION
import com.xyz.oclock.Versions.BINDABLES_VERSION
import com.xyz.oclock.Versions.CONSTRAINT_LAYOUT_VERSION
import com.xyz.oclock.Versions.CORE_KTX_VERSION
import com.xyz.oclock.Versions.FIREBASE_MESSAGING
import com.xyz.oclock.Versions.GLIDE_VERSION
import com.xyz.oclock.Versions.GOOGLE_SERVICE_VERSION
import com.xyz.oclock.Versions.HILT_VERSION
import com.xyz.oclock.Versions.LEGACY_SUPPORT_VERSION
import com.xyz.oclock.Versions.LIFECYCLE_VERSION
import com.xyz.oclock.Versions.MATERIAL_VERSION
import com.xyz.oclock.Versions.NAV_VERSION
import com.xyz.oclock.Versions.PICK_IMAGE_VERSION
import com.xyz.oclock.Versions.PLAY_SERVICE_CAST_VERSION
import com.xyz.oclock.Versions.SECURITY_CRYPTO_VERSION
import com.xyz.oclock.Versions.VIEW_PAGER_VERSION

object Versions {
    const val NAV_VERSION = "2.5.1"
    const val GLIDE_VERSION = "4.13.2"
    const val HILT_VERSION = "2.38.1"
    const val VIEW_PAGER_VERSION = "1.0.0"
    const val BINDABLES_VERSION = "1.1.0"
    const val GOOGLE_SERVICE_VERSION = "4.3.5"
    const val FIREBASE_MESSAGING = "23.0.7"
    const val PICK_IMAGE_VERSION = "3.0.01"
    const val SECURITY_CRYPTO_VERSION = "1.1.0-alpha03"
    const val CORE_KTX_VERSION = "1.8.0"
    const val APPCOMPAT_VERSION = "1.5.0"
    const val MATERIAL_VERSION = "1.6.1"
    const val CONSTRAINT_LAYOUT_VERSION = "2.1.4"
    const val LEGACY_SUPPORT_VERSION = "1.0.0"
    const val LIFECYCLE_VERSION = "2.5.1"
    const val PLAY_SERVICE_CAST_VERSION = "20.0.0"
}

object Glide {
    const val GLIDE = "com.github.bumptech.glide:glide:$GLIDE_VERSION"
    const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:$GLIDE_VERSION"
}

object Navigation {
    const val NAVIGATION_FRAGMENT = "androidx.navigation:navigation-fragment:$NAV_VERSION"
    const val NAVIGATION_UI = "androidx.navigation:navigation-ui:$NAV_VERSION"
    const val NAVIGATION_FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:$NAV_VERSION"
    const val NAVIGATION_UI_KTX = "androidx.navigation:navigation-ui-ktx:$NAV_VERSION"
    const val NAVIGATION_DYNAMIC_FEATURES_FRAGMENT = "androidx.navigation:navigation-dynamic-features-fragment:$NAV_VERSION"
    const val NAVIGATION_TESTING = "androidx.navigation:navigation-testing:$NAV_VERSION"
    const val NAVIGATION_COMPOSE = "androidx.navigation:navigation-compose:$NAV_VERSION"
    const val NAVIGATION_SAFE_ARGS = "androidx.navigation:navigation-safe-args-gradle-plugin:$NAV_VERSION"
}

object Hilt {
    const val HILT_ANDROID_GRADLE_PLUGIN = "com.google.dagger:hilt-android-gradle-plugin:$HILT_VERSION"
    const val HILT_ANDROID = "com.google.dagger:hilt-android:$HILT_VERSION"
    const val HILT_COMPILER = "com.google.dagger:hilt-compiler:$HILT_VERSION"
}

object ViewPager{
    const val VIEW_PAGER = "androidx.viewpager2:viewpager2:$VIEW_PAGER_VERSION"
}

object Bindables {
    const val BINDABLES = "com.github.skydoves:bindables:$BINDABLES_VERSION"
}

object Google {
    const val SERVICE = "com.google.gms:google-services:$GOOGLE_SERVICE_VERSION"
}

object Firebase {
    const val MESSAGING = "com.google.firebase:firebase-messaging:$FIREBASE_MESSAGING"
}

object PickImage {
    const val PICK_IMAGE = "com.github.jrvansuita:PickImage:$PICK_IMAGE_VERSION"
}

object Security {
    const val CRYPTO_KTX = "androidx.security:security-crypto-ktx:$SECURITY_CRYPTO_VERSION"
}

object Core {
    const val CORE_KTX = "androidx.core:core-ktx:$CORE_KTX_VERSION"
}

object Appcompat {
    const val APPCOMPAT = "androidx.appcompat:appcompat:$APPCOMPAT_VERSION"
}

object Material {
    const val MATERIAL = "com.google.android.material:material:$MATERIAL_VERSION"
}

object ConstraintLayout {
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:$CONSTRAINT_LAYOUT_VERSION"
}

object Legacy {
    const val LEGACY_SUPPORT = "androidx.legacy:legacy-support-v4:$LEGACY_SUPPORT_VERSION"
}

object Lifecycle {
    const val LIVEDATA_KTX= "androidx.lifecycle:lifecycle-livedata-ktx:$LIFECYCLE_VERSION"
    const val VIEWMODEL_KTX= "androidx.lifecycle:lifecycle-viewmodel-ktx:$LIFECYCLE_VERSION"
}

object Gms {
    const val PLAY_SERVICE_CAST = "com.google.android.gms:play-services-cast-framework:$PLAY_SERVICE_CAST_VERSION"
}

object Test{
    const val JUNIT = "junit:junit:4.13.2"
    const val EXT_JUNIT = "androidx.test.ext:junit:1.1.3"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:3.4.0"
}