package com.abhijith.feature_auth.presentation.viewmodels

import com.abhijith.feature_auth.domain.usecase.AlphaNumericValidation
import com.abhijith.feature_auth.domain.usecase.EmailValidationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun onEmailChanged(){
        runBlocking {
            val vm = LoginViewModel(
                emailValidationUseCase = EmailValidationUseCase(),
                alphaNumericValidationForPassword = AlphaNumericValidation(),
                thread = TestCoroutineDispatcher()
            )
            vm.isShouldStartValidationEmission = true
            vm.onEmailChanged("abhialur8898@gmail.com")
            assert(vm.emailValidationErrorMessage.first().first){
                vm.emailValidationErrorMessage.value.second
            }
            vm.onEmailChanged("abhialur8898gmail.com")
            assert(!vm.emailValidationErrorMessage.first().first){
                vm.emailValidationErrorMessage.value.second
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun onPasswordChanged(){
        runBlocking {
            val vm = LoginViewModel(
                emailValidationUseCase = EmailValidationUseCase(),
                alphaNumericValidationForPassword = AlphaNumericValidation(),
                thread = TestCoroutineDispatcher()
            )
            vm.isShouldStartValidationEmission = true
            vm.onPasswordChanged("abhialur8898")
            assert(vm.passwordValidationErrorMessage.first().first){
                vm.emailValidationErrorMessage.value.second
            }
            vm.onPasswordChanged("abhialur")
            assert(!vm.passwordValidationErrorMessage.first().first){
                vm.emailValidationErrorMessage.value.second
            }
        }
    }


}