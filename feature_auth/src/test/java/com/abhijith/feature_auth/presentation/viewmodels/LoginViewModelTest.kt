package com.abhijith.feature_auth.presentation.viewmodels

import com.abhijith.core.utility.InputState
import com.abhijith.feature_auth.domain.usecase.EmailValidationUseCase
import com.abhijith.feature_auth.domain.usecase.PasswordValidation
import com.abhijith.feature_auth.utility.LoginState
import com.androidTest.mocks.data.repo.fake.TestAuthenticationRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {


    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun onEmailChanged() {
        runBlocking {
            val vm = LoginViewModel(
                emailValidationUseCase = EmailValidationUseCase(),
                passwordValidation = PasswordValidation(),
                thread = TestCoroutineDispatcher(),
                authenticationRepo = TestAuthenticationRepo.getInstance()
            )
            vm.isShouldStartValidationEmission = true
            vm.onEmailChanged("abhialur8898@gmail.com")
            assertTrue(vm.emailValidationErrorMessage.first() == InputState.VALID)
            vm.onEmailChanged("abhialur8898gmail.com")
            assertTrue(vm.emailValidationErrorMessage.first() != InputState.VALID)
        }
    }

    @Test
    fun onPasswordChanged() {
        runBlocking {
            val vm = LoginViewModel(
                emailValidationUseCase = EmailValidationUseCase(),
                passwordValidation = PasswordValidation(),
                thread = TestCoroutineDispatcher(),
                authenticationRepo = TestAuthenticationRepo.getInstance()
            )
            print("LogIsWorking")
            vm.isShouldStartValidationEmission = true
            vm.onPasswordChanged("abhialur8898")
            assertTrue(vm.passwordValidationErrorMessage.first() == InputState.VALID)
            vm.onPasswordChanged("abhiaur")
            assertTrue(vm.passwordValidationErrorMessage.first() != InputState.VALID)
        }
    }

    @Test
    fun onLoginClick_valid_data(){
        runBlocking {
            val vm = LoginViewModel(
                emailValidationUseCase = EmailValidationUseCase(),
                passwordValidation = PasswordValidation(),
                thread = TestCoroutineDispatcher(),
                authenticationRepo = TestAuthenticationRepo.getInstance()
            )
            vm.onEmailChanged(TestAuthenticationRepo.valid_data.first)
            vm.onPasswordChanged(TestAuthenticationRepo.valid_data.second)
            vm.onLoginClick()
            assert(vm.emailValidationErrorMessage.first() == InputState.VALID)
            assert(vm.loginStateFlow.first() == LoginState.LoggedIn)
        }
    }

    @Test
    fun onLoginClick_in_valid_data(){
        runBlocking {
            val vm = LoginViewModel(
                emailValidationUseCase = EmailValidationUseCase(),
                passwordValidation = PasswordValidation(),
                thread = TestCoroutineDispatcher(),
                authenticationRepo = TestAuthenticationRepo.getInstance()
            )
            vm.onEmailChanged(TestAuthenticationRepo.in_valid_data.first)
            vm.onPasswordChanged(TestAuthenticationRepo.in_valid_data.second)
            vm.onLoginClick()
            assert(vm.loginStateFlow.first() == LoginState.LoggedOut)
        }
    }

}