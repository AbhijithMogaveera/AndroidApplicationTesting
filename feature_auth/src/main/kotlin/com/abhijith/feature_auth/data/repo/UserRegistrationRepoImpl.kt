package com.abhijith.feature_auth.data.repo

import arrow.core.Either
import com.abhijith.feature_auth.domain.model.RegistrationData
import com.abhijith.feature_auth.domain.repo.UserRegistrationRepo

class UserRegistrationRepoImpl(

) : UserRegistrationRepo {
    override fun registerUser(
        emailId: String,
        password: String
    ): Either<Throwable, RegistrationData> = Either.catch {
        TODO()
    }

    override fun deleteAccount(emailId: String): Either<Throwable, Boolean> = Either.catch {
        TODO()
    }
}