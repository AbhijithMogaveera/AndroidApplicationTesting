package com.abhijith.feature_auth.domain.usecase

import com.abhijith.feature_auth.utility.validateForEmail
import javax.inject.Inject

class EmailValidationUseCase
@Inject constructor(

) {
    suspend fun validate(email:String):Boolean = email.validateForEmail()
}