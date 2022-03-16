package com.abhijith.feature_auth.data.repo

import arrow.core.Either
import com.abhijith.feature_auth.data.source.remote.AuthenticationApi
import com.abhijith.feature_auth.data.source.remote.model.request.LoginRequest
import com.abhijith.feature_auth.domain.model.LoginData
import com.abhijith.feature_auth.domain.repo.AuthenticationRepo
import com.abhijith.feature_auth.domain.repo.CredentialDataStoreRepo
import javax.inject.Inject

class DefaultAuthenticationRepo
@Inject constructor(
    private val authenticationApi: AuthenticationApi,
    private val credentialDataStoreRepo:CredentialDataStoreRepo
) : AuthenticationRepo {

    override suspend fun login(
        userId:String,
        password:String
    ): Either<Throwable, Unit> = Either.catch {
        authenticationApi.login(
            LoginRequest(
                emailId = userId,
                password = password,
                deviceID = ""
            )
        ).let {
            credentialDataStoreRepo.saveLoginData(LoginData(userId, it.data.authToken))
        }
    }

}