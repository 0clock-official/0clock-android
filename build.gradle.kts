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

subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
        kotlinOptions.freeCompilerArgs += listOf(
            "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-Xopt-in=kotlin.time.ExperimentalTime",
        )
        kotlinOptions.freeCompilerArgs += listOf("-Xjvm-default=all")
    }
}
