package com.abhijith.feature_auth.presentation.viewmodels

import android.util.Log
import com.abhijith.core.utility.InputState
import com.abhijith.feature_auth.domain.usecase.EmailValidationUseCase
import com.abhijith.feature_auth.domain.usecase.PasswordValidation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.runners.model.Statement

@RunWith(JUnit4::class)
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
                thread = TestCoroutineDispatcher()
            )
            vm.isShouldStartValidationEmission = true
            vm.onEmailChanged("abhialur8898@gmail.com")
            assertTrue(vm.emailValidationErrorMessage.first() == InputState.VALID)
            vm.onEmailChanged("abhialur8898gmail.com")
            assertTrue(vm.emailValidationErrorMessage.first() != InputState.VALID)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun onPasswordChanged() {
        runBlocking {
            val vm = LoginViewModel(
                emailValidationUseCase = EmailValidationUseCase(),
                passwordValidation = PasswordValidation(),
                thread = TestCoroutineDispatcher()
            )
            print("LogIsWorking")
            vm.isShouldStartValidationEmission = true
            vm.onPasswordChanged("abhialur8898")
            assertTrue(vm.passwordValidationErrorMessage.first() == InputState.VALID)
            vm.onPasswordChanged("abhiaur")
            assertTrue(vm.passwordValidationErrorMessage.first() != InputState.VALID)
        }
    }


}