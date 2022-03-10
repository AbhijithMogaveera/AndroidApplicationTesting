package com.abhijith.feature_auth.domain.repo

import android.service.autofill.UserData
import com.abhijith.feature_auth.data.source.local.model.UserCredentialData
import com.abhijith.feature_auth.domain.model.LoginData
import kotlinx.coroutines.flow.Flow

interface CredentialDataStoreRepo {
    suspend fun getUserLoginFlow(): Flow<Boolean>
    suspend fun saveLoginData(userData: LoginData)
    suspend fun editLoginData(userData: LoginData)
    suspend fun clearLoginData()
    suspend fun getCredentialData():UserCredentialData
}