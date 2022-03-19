package com.abhijith.feature_auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.abhijith.feature_auth.domain.usecase.EmailValidationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject constructor(
    val emailValidationUseCase: EmailValidationUseCase
):ViewModel() {

}