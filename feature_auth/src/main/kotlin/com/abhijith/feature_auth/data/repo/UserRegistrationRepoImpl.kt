package com.abhijith.feature_auth.data.repo

import arrow.core.Either
import com.abhijith.feature_auth.data.source.remote.RegistrationApi
import com.abhijith.feature_auth.data.source.remote.model.request.RegistrationRequest
import com.abhijith.feature_auth.domain.model.RegistrationData
import com.abhijith.feature_auth.domain.repo.UserRegistrationRepo
import javax.inject.Inject

class UserRegistrationRepoImpl
@Inject constructor(
    val registrationApi: RegistrationApi
) : UserRegistrationRepo {

    override suspend fun registerUser(
        emailId: String,
        password: String
    ): Either<Throwable, RegistrationData> = Either.catch {
        registrationApi.register(
            RegistrationRequest(
                emailId,
                password,
            ""
            )
        )
    }.map {
        RegistrationData(
            emailId, it.data.authToken
        )
    }

    override suspend fun deleteAccount(emailId: String): Either<Throwable, Boolean> = Either.catch {
        TODO()
    }
}