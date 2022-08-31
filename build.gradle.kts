buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.agp)
        classpath(libs.hilt.plugin)
        classpath(libs.androidx.navigation.safeargs.plugin)
        classpath(libs.gms.google.service)
    }
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}
