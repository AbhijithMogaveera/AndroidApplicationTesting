package com.abhijith.feature_auth.data.repo

import com.abhijith.feature_auth.data.source.local.model.UserCredentialData
import com.abhijith.feature_auth.domain.model.LoginData
import com.abhijith.feature_auth.domain.repo.CredentialDataStoreRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.lang.IllegalStateException

class CredentialDataStoreRepoTestDouble : CredentialDataStoreRepo {
    private val loginDataList = MutableStateFlow<LoginData?>(null)
    override suspend fun getUserLoginFlow(): Flow<Boolean> = loginDataList.map {
        it != null
    }

    override suspend fun saveLoginData(userData: LoginData) {
        loginDataList.emit(userData)
    }

    override suspend fun editLoginData(userData: LoginData) {
        loginDataList.emit(userData)
    }

    override suspend fun clearLoginData() {
        loginDataList.emit(null)
    }

    override suspend fun getCredentialData(): UserCredentialData {
        return loginDataList.first()?.let {
            UserCredentialData(
            it.email,
            it.authToken,
        ) }?: throw IllegalStateException()
    }
}