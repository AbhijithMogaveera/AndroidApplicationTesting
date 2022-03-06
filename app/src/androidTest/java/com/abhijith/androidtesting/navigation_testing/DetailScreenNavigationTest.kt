package com.abhijith.androidtesting.navigation_testing

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.abhijith.androidtesting.notes.NoteListingFragment
import com.abhijith.androidtesting.R
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailScreenNavigationTest {

    @Test
    fun testNavigationIntoDetailsScreen() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val testScenario = launchFragmentInContainer<NoteListingFragment>()
        testScenario.onFragment{
            navController.setGraph(R.navigation.main_screen_navigation)
            Navigation.setViewNavController(it.requireView(), navController)
        }
        onView(ViewMatchers.withId(R.id.btnGotoDetailsScreen)).perform(click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.note_detail_fragment)

    }
}