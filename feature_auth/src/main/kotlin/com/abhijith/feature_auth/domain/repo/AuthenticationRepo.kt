package com.abhijith.feature_auth.domain.repo

import arrow.core.Either
import com.abhijith.feature_auth.domain.model.LoginData

interface AuthenticationRepo {
    suspend fun login(
        userId:String,
        password:String
    ): Either<Throwable, Unit>
}