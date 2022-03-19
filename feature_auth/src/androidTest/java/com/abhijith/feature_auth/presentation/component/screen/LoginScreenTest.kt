package com.abhijith.feature_auth.presentation.component.screen

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.internal.matchers.Null

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {

    }

    @Test
    fun checkViewRecycling() {
        val sernario = launchFragmentInContainer<LoginScreen>()
        sernario.moveToState(Lifecycle.State.CREATED)
        sernario.onFragment {
            try {
                it.viewBinding
                assert(false)
            } catch (e: Exception) {
                assert(e is NullPointerException)
            }
            sernario.moveToState(Lifecycle.State.CREATED)

            sernario.moveToState(Lifecycle.State.DESTROYED)
            try {
                it.viewBinding
                assert(false)
            } catch (e: Exception) {
                assert(e is NullPointerException)
            }
        }
    }

}