package com.abhijith.feature_auth.domain.repo

import arrow.core.Either
import com.abhijith.feature_auth.domain.model.LoginData

interface AuthenticationRepo {
    fun login(loginData: LoginData): Either<Throwable, Unit>
}