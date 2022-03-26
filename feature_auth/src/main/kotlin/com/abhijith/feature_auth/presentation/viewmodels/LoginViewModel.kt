package com.abhijith.feature_auth.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.abhijith.core.utility.InputState
import com.abhijith.feature_auth.data.repo.DefaultRegistrationRepoImpl
import com.abhijith.feature_auth.domain.repo.AuthenticationRepo
import com.abhijith.feature_auth.domain.repo.UserRegistrationRepo
import com.abhijith.feature_auth.domain.usecase.EmailValidationUseCase
import com.abhijith.feature_auth.domain.usecase.PasswordValidation
import com.abhijith.feature_auth.utility.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject constructor(
    private val emailValidationUseCase: EmailValidationUseCase,
    private val passwordValidation: PasswordValidation,
    private val thread: CoroutineDispatcher,
    val authenticationRepo: AuthenticationRepo
) : ViewModel() {

    var isShouldStartValidationEmission: Boolean = true

    private val _emailValidationErrorMessage = MutableStateFlow(InputState.UN_INITIALIZED)
    val emailValidationErrorMessage = _emailValidationErrorMessage

    private val _passwordValidationErrorMessage = MutableStateFlow(InputState.UN_INITIALIZED)
    val passwordValidationErrorMessage = _passwordValidationErrorMessage

    private val myEmail: String = ""
    private val myPassWord: String = ""

    fun onEmailChanged(email: String) {
        if (!isShouldStartValidationEmission)
            return
        viewModelScope.launch(thread) {
            if (email.isEmpty()) {
                _emailValidationErrorMessage.emit(InputState.BLANK)
                return@launch
            }
            if (!emailValidationUseCase.validate(email)) {
                _emailValidationErrorMessage.emit(InputState.INVALID)
                return@launch
            }
            _emailValidationErrorMessage.emit(InputState.VALID)
        }
    }

    fun onPasswordChanged(password: String) {
        if (!isShouldStartValidationEmission)
            return
        viewModelScope.launch(thread) {
            if (password.isEmpty()) {
                _passwordValidationErrorMessage.emit(InputState.BLANK)
                return@launch
            }
            if (!passwordValidation.validate(password)) {
                _passwordValidationErrorMessage.emit(InputState.INVALID)
                return@launch
            }
            _passwordValidationErrorMessage.emit(InputState.VALID)
        }
    }

    private val _loginStateFlow:MutableStateFlow<LoginState> = MutableStateFlow(LoginState.LoggedOut)
    val loginStateFlow:Flow<LoginState> = _loginStateFlow.asStateFlow()
    fun onLoginClick() {
        viewModelScope.launch(thread) {
            isShouldStartValidationEmission = true
            onEmailChanged(myEmail)
            onPasswordChanged(myPassWord)
            val isValidEmail = emailValidationErrorMessage.first() == InputState.VALID
            if (isValidEmail) {
                when(authenticationRepo.login(myEmail, myPassWord)){
                    is Either.Left -> {
                        _loginStateFlow.emit(LoginState.LoggedOut)
                    }
                    is Either.Right -> {
                        _loginStateFlow.emit(LoginState.LoggedIn)
                    }
                }
            }
        }
    }

}