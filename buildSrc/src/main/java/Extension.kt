import org.gradle.kotlin.dsl.DependencyHandlerScope

internal fun DependencyHandlerScope.impl(dependencyNotation:String){
    add("implementation", dependencyNotation)
}

internal fun DependencyHandlerScope.testImpl(dependencyNotation:String){
    add("testImplementation", dependencyNotation)
}

internal fun DependencyHandlerScope.androidTestImpl(dependencyNotation:String){
    add("androidTestImplementation", dependencyNotation)
}

internal fun DependencyHandlerScope.kapt(dependencyNotation:String){
    add("kapt", dependencyNotation)
}

internal fun DependencyHandlerScope.kaptAndroidTest(dependencyNotation:String){
    add("kaptAndroidTest", dependencyNotation)
}

internal fun DependencyHandlerScope.androidTestAnnotationProcessor(dependencyNotation:String){
    add("androidTestAnnotationProcessor", dependencyNotation)
}