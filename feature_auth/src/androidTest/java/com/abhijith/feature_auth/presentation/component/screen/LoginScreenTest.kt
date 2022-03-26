package com.abhijith.feature_auth.presentation.component.screen

import android.content.Context
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.abhijith.androidtesting.launchFragmentInHiltContainer
import com.abhijith.feature_auth.R
import com.abhijith.feature_auth.di.RepoModuleTest
import com.abhijith.feature_auth.utility.LoginState
import com.androidTest.mocks.data.repo.fake.TestAuthenticationRepo
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@HiltAndroidTest
class LoginScreenTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var instrumentContext: Context

    @Before
    fun setUp() {
        instrumentContext = InstrumentationRegistry.getInstrumentation().context
    }

    @After
    fun tearDown() {

    }


    @Test
    fun testForEmailValidation_valid_email() {
        //Arrange
        val validEmail = "abhialur8898@gmail.com"

        //Act
        launchFragmentInHiltContainer<LoginScreen>()
        Espresso
            .onView(ViewMatchers.withId(R.id.et_email_input))
            .perform(ViewActions.typeText(validEmail))

        //Assert
        Espresso
            .onView(ViewMatchers.withId(R.id.tv_email_input_error))
            .check(ViewAssertions.matches(ViewMatchers.withText("")))

    }

    @Test
    fun testForEmailValidation_in_valid_email() {
        //Arrange
        val validEmail = "abhialur8898@gmailcom"

        //Act
        launchFragmentInHiltContainer<LoginScreen>()
        Espresso
            .onView(ViewMatchers.withId(R.id.et_email_input))
            .perform(ViewActions.typeText(validEmail))

        //Assert
        Espresso
            .onView(ViewMatchers.withId(R.id.tv_email_input_error))
            .check(ViewAssertions.matches(ViewMatchers.withText(instrumentContext.getString(R.string.invalid_email_id))))

    }

    @Test
    fun testForPasswordValidation() {

    }

    @Test
    fun testForValidLogin() {
        runBlocking {
            RepoModuleTest.providesAuthenticationRepo()
            //Arrange
            val validEmail = "abhialur8898@gmailcom"

            //Act
            var loginFragment: LoginScreen? = null
            launchFragmentInHiltContainer<LoginScreen>() {
                loginFragment = this as LoginScreen
            }
            loginFragment!!.let { loginScreen ->
                Espresso
                    .onView(ViewMatchers.withId(R.id.et_email_input))
                    .perform(ViewActions.typeText(TestAuthenticationRepo.valid_data.first),ViewActions.closeSoftKeyboard())
                Espresso
                    .onView(ViewMatchers.withId(R.id.et_password_input))
                    .perform(ViewActions.typeText(TestAuthenticationRepo.valid_data.second), ViewActions.closeSoftKeyboard())

                Espresso
                    .onView(ViewMatchers.withId(R.id.btn_login))
                    .perform(ViewActions.click())
                val collection = loginScreen.loginViewModel.loginStateFlow.collect {
                    when(it){
                        LoginState.LoggedIn -> {
                            assert(true)
                        }
                        LoginState.LoggedOut -> {
                            assert(false)
                        }
                    }
                }
            }


            //Assert
            Espresso
                .onView(ViewMatchers.withId(R.id.tv_email_input_error))
                .check(ViewAssertions.matches(ViewMatchers.withText(instrumentContext.getString(R.string.invalid_email_id))))

        }

    }

    @Test
    fun testFakeValidLogin() {

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
