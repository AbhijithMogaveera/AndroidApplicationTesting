import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.addRoomDependency(needTestDependency: Boolean = true) {

    val roomVersion = "2.4.2"

    impl("androidx.room:room-runtime:$roomVersion")

    if (needTestDependency)
        testImpl("androidx.room:room-testing:$roomVersion")

    kapt("androidx.room:room-compiler:$roomVersion")
    impl("androidx.room:room-ktx:$roomVersion")

}

fun DependencyHandlerScope.addMaterialLibDependency() {
    impl("com.google.android.material:material:1.5.0")
}

fun DependencyHandlerScope.addHiltDependency(
    needTestDependency: Boolean = true,
    needComposeSupport:Boolean = false
) {
    val hiltVersion = "2.38.1"
    impl("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    if(needTestDependency){
        androidTestAnnotationProcessor("com.google.dagger:hilt-android-compiler:$hiltVersion")
        androidTestImpl("com.google.dagger:hilt-android-testing:$hiltVersion")
        testImpl("com.google.dagger:hilt-android-testing:$hiltVersion")
        kaptAndroidTest("com.google.dagger:hilt-android-compiler:$hiltVersion")
        testImpl("com.google.dagger:hilt-android-compiler:$hiltVersion")
    }
    if(needComposeSupport){
        impl("androidx.hilt:hilt-navigation-compose:1.0.0")
    }
}

fun DependencyHandlerScope.addArrowDependency() {
    impl("io.arrow-kt:arrow-optics:${Version.arrow_version}")
    kapt("io.arrow-kt:arrow-meta:${Version.arrow_version}")
}

fun DependencyHandlerScope.addSquareUpDependency() {
    testImpl("com.squareup.okhttp3:mockwebserver:4.9.3")
    impl("com.squareup.retrofit2:retrofit:2.9.0")
    impl("com.squareup.retrofit2:converter-gson:2.9.0")
}

fun DependencyHandlerScope.addCoroutinesDependency() {
    impl("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutine_version}")
    impl("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutine_version}")
    testImpl("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.coroutine_version}")
//    androidTestImpl("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.coroutine_version}")
}

fun DependencyHandlerScope.addDataStoreDependency() {
    impl("androidx.datastore:datastore:${Version.data_store_version}")
    impl("androidx.datastore:datastore-preferences:${Version.data_store_version}")
}

fun DependencyHandlerScope.addNavigationDependency() {
    impl("androidx.navigation:navigation-fragment-ktx:2.4.1")
    impl("androidx.navigation:navigation-ui-ktx:2.4.1")
    androidTestImpl("androidx.navigation:navigation-testing:2.4.1")
}

fun DependencyHandlerScope.addAndroidXTestDependency() {
    testImpl("androidx.test:core:1.4.0")
    androidTestImpl("androidx.test.ext:junit:1.1.3")
    androidTestImpl("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImpl("androidx.test.espresso:espresso-contrib:3.4.0")
    androidTestImpl("androidx.test.ext:junit:1.1.3")
    androidTestImpl("androidx.test.ext:junit:1.1.3")
    testImpl("androidx.arch.core:core-testing:2.1.0")
    androidTestImpl("androidx.arch.core:core-testing:2.1.0")
}

fun DependencyHandlerScope.addJUnitTestImpl() {
    testImpl("junit:junit:4.13.2")
    androidTestImpl("junit:junit:4.13.2")
}

fun DependencyHandlerScope.addGoogleTruth() {
    androidTestImpl("com.google.truth:truth:1.0.1")
    testImpl("com.google.truth:truth:1.0.1")
}

fun DependencyHandlerScope.addMockitoTestDependency() {
    testImpl("org.mockito:mockito-core:3.9.0")
}

fun DependencyHandlerScope.addRobolectric() {
    testImpl("org.robolectric:robolectric:4.4")
}

fun DependencyHandlerScope.addHamcrest() {
    testImpl("org.hamcrest:hamcrest-all:1.3")
}

fun DependencyHandlerScope.addFragmentTesting() {
    val fragment_version = "1.4.1"
    debugImplementation("androidx.fragment:fragment-testing:$fragment_version")
}

fun DependencyHandlerScope.addComposeDependency() {
    impl("androidx.compose.ui:ui:${Version.compose_version}")
    impl("androidx.compose.material:material:${Version.compose_version}")
    impl("androidx.compose.ui:ui-tooling-preview:${Version.compose_version}")
    impl("androidx.activity:activity-compose:1.3.1")
    androidTestImpl("androidx.compose.ui:ui-test-junit4:${Version.compose_version}")
    debugImplementation("androidx.compose.ui:ui-tooling:${Version.compose_version}")
}

fun DependencyHandlerScope.addCoreKTX(){
    impl("androidx.fragment:fragment-ktx:1.4.1")
}