plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id(Plugin.navigationSafeArgs)
    id(Plugin.hilt)
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
    addMaterialLibDependency()
    implementation("androidx.activity:activity-ktx:1.5.0-alpha03")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")

    addRoomDependency()
    addHiltDependency()
    addArrowDependency()
    addCoroutinesDependency()
    addSquareUpDependency()
    addDataStoreDependency()
    addNavigationDependency()

    //test
    addAndroidXTestDependency()
    addJUnitTestImpl()
    addMockitoTestDependency()
    addHamcrest()
    addRobolectric()
    addGoogleTruth()
    addFragmentTesting()
    androidTestImplementation("com.linkedin.dexmaker:dexmaker-mockito:2.28.1")

}
kapt {
    correctErrorTypes = true
}
