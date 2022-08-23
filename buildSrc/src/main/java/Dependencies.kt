import Versions.GLIDE_VERSION
import Versions.HILT_VERSION
import Versions.NAV_VERSION
import Versions.VIEW_PAGER_VERSION

object Versions {
    const val NAV_VERSION = "2.5.1"
    const val GLIDE_VERSION = "4.13.2"
    const val HILT_VERSION = "2.38.1"
    const val VIEW_PAGER_VERSION = "1.0.0"
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