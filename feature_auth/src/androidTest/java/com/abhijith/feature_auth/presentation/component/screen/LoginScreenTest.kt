package com.abhijith.feature_auth.presentation.component.screen

import android.util.Log
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.abhijith.androidtesting.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class LoginScreenTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {

    }

    @Test
    fun checkViewRecycling() {
        launchFragmentInHiltContainer<LoginScreen> {
            assert( this is LoginScreen)
            if(this is LoginScreen){
                this.loginViewModel
                print("-----------------------------------------")
                Log.e("Hello",this.viewBinding.loginElements.tvError.text.toString())
                print("-----------------------------------------")
            }
            /*if (this is LoginScreen) {
                Espresso.onView(
                    withId(
                        viewBinding.loginElements.editText.id
                    )
                ).perform(
                    ViewActions
                        .typeText("abhialur8898@gmail.com")
                ).check(
                    ViewAssertions
                        .matches(
                            ViewMatchers
                                .isDisplayed()
                        )
                )
            }*/
        }
    }

}
/*

inline fun <reified T : Fragment> launchFragmentInHiltContainer(
    fragmentArgs: Bundle? = null,
    @StyleRes themeResId: Int = R.style.FragmentScenarioEmptyFragmentActivityTheme,
    crossinline action: Fragment.() -> Unit = {}
) {
    val startActivityIntent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            HiltTestActivity::class.java
        )
    ).putExtra(
        "androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY",
        themeResId
    )

    ActivityScenario.launch<HiltTestActivity>(startActivityIntent).onActivity { activity ->
        val fragment: Fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
            Preconditions.checkNotNull(T::class.java.classLoader),
            T::class.java.name
        )
        fragment.arguments = fragmentArgs
        activity.supportFragmentManager
            .beginTransaction()
            .add(android.R.id.content, fragment, "")
            .commitNow()

        fragment.action()
    }
}*/
