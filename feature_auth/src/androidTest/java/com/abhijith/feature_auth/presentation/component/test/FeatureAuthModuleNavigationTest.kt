package com.abhijith.feature_auth.presentation.component.test

import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.abhijith.androidtesting.launchFragmentInHiltContainer
import com.abhijith.feature_auth.R
import com.abhijith.feature_auth.presentation.component.screen.LoginScreen
import com.abhijith.feature_auth.presentation.component.screen.SignUpScreen
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class FeatureAuthModuleNavigationTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun test_navigation_into_signup_screen() {
        runBlocking {
            val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
            var loginScreen:LoginScreen?=null
            launchFragmentInHiltContainer<LoginScreen>(){
                loginScreen = this as LoginScreen
            }
            withContext(Dispatchers.Main){
                loginScreen!!.also{
                    navController.setGraph(R.navigation.auth_navigation)
                    Navigation.setViewNavController(it.requireView(), navController)
                }
            }
            onView(ViewMatchers.withId(R.id.btn_goto_signup)).perform(click())
            assertThat(navController.currentDestination?.id).isEqualTo(R.id.screen_signup)
        }
    }

    @Test
    fun test_navigation_into_login_screen_from_signup() {
        runBlocking {
            val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
            var loginScreen:SignUpScreen?=null
            launchFragmentInHiltContainer<SignUpScreen>(){
                loginScreen = this as SignUpScreen
            }
            withContext(Dispatchers.Main){
                loginScreen!!.also{
                    navController.setGraph(R.navigation.auth_navigation)
                    Navigation.setViewNavController(it.requireView(), navController)
                }
                navController.setCurrentDestination(R.id.screen_signup)
            }
            onView(ViewMatchers.withId(R.id.btn_goto_login)).perform(click())
            assertThat(navController.currentDestination?.id).isEqualTo(R.id.screen_login)
        }
    }

}