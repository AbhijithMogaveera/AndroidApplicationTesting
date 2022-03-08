package com.abhijith.feature_auth.data.repo

import arrow.core.Either
import com.abhijith.feature_auth.domain.model.LoginData
import com.abhijith.feature_auth.domain.repo.AuthenticationRepo
import javax.inject.Inject

class AuthenticationRepoImpl(

):AuthenticationRepo {
    override fun login(loginData: LoginData)= Either.catch {

    }
}