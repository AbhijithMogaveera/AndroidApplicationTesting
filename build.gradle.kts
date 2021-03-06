buildscript {
    repositories {
        google()
    }
    dependencies {
        val nav_version = "2.4.1"
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
//        classpath ("com.google.protobuf:protobuf-gradle-plugin:0.8.10")
    }
}
plugins {
    id ("com.android.application") version "7.1.1" apply false
    id ("com.android.library") version "7.1.1" apply false
    id ("org.jetbrains.kotlin.android") version "1.6.10" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
