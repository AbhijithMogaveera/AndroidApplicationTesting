import Version.arrow_version
import Version.coroutine_version
import Version.data_store_version
import Version.room_version
//import com.google.protobuf.gradle.generateProtoTasks
//import com.google.protobuf.gradle.ofSourceSet
//import com.google.protobuf.gradle.protobuf
//import com.google.protobuf.gradle.protoc

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id(Plugin.navigationSafeArgs)
    id(Plugin.hilt)
//    id(Plugin.protoBuf)
    id("kotlin-kapt")

}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 32
        testInstrumentationRunner = "com.abhijith.feature_auth.CustomTestRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(path = ":core"))
    implementation("androidx.cardview:cardview:1.0.0")

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    //dagger hilt
    implementation("com.google.dagger:hilt-android:2.38.1")
    kapt("com.google.dagger:hilt-android-compiler:2.38.1")

    implementation("io.arrow-kt:arrow-optics:$arrow_version")
    kapt("io.arrow-kt:arrow-meta:$arrow_version")


    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    implementation("androidx.datastore:datastore:$data_store_version")
    implementation("androidx.datastore:datastore-preferences:$data_store_version")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutine_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutine_version")
//    implementation("com.google.protobuf:protobuf-javalite:3.19.4")



    // Local Unit Tests
    implementation("androidx.test:core:1.4.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.hamcrest:hamcrest-all:1.3")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("org.robolectric:robolectric:4.4")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
    testImplementation("com.google.truth:truth:1.0.1")
    testImplementation("org.mockito:mockito-core:3.9.0")

    // Instrumented Unit Tests
    androidTestImplementation("junit:junit:4.13.2")
    androidTestImplementation("com.linkedin.dexmaker:dexmaker-mockito:2.28.1")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation("com.google.truth:truth:1.0.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("org.mockito:mockito-core:3.9.0")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.28-alpha")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.37")
    debugImplementation("androidx.fragment:fragment-testing:1.5.0-alpha03")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.4.0")

    // For instrumented tests.
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.38.1")
    // ...with Kotlin.
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.38.1")
    // ...with Java.
    androidTestAnnotationProcessor("com.google.dagger:hilt-android-compiler:2.38.1")

    testImplementation("com.google.dagger:hilt-android-testing:2.38.1")
    // ...with Kotlin.
    kaptTest("com.google.dagger:hilt-android-compiler:2.38.1")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.3")


}
kapt {
    correctErrorTypes = true
}

