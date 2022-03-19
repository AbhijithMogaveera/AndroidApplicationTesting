package com.abhijith.feature_auth.domain.usecase

import com.abhijith.feature_auth.utility.validateForEmail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class EmailValidationUseCase
@Inject constructor(

) {
    val startContinuousValidation:Boolean = false
    private val _flowOfValidationResults = MutableStateFlow(false)
    val flowOfValidationResults = _flowOfValidationResults.asStateFlow()
    suspend fun validate(email:String){
        _flowOfValidationResults.emit(email.validateForEmail())
    }
}