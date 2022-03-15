package com.abhijith.feature_auth.domain.repo

import arrow.core.Either
import com.abhijith.feature_auth.domain.model.RegistrationData

interface UserRegistrationRepo {

    /*returns authToken */
    suspend fun registerUser(
        emailId: String,
        password: String
    ): Either<Throwable, RegistrationData>

    suspend fun deleteAccount(emailId: String): Either<Throwable, Boolean>
}