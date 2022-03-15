package com.abhijith.feature_auth.data.repo

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.abhijith.feature_auth.data.source.local.model.UserCredentialData
import com.abhijith.feature_auth.domain.model.LoginData
import com.abhijith.feature_auth.domain.repo.CredentialDataStoreRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Application.dataStore: DataStore<Preferences> by preferencesDataStore(name = "credential_data_store")

class DefaultCredentialDataStoreRepo(
    private val application: Application,
) : CredentialDataStoreRepo {

    private val isLoggedIn = booleanPreferencesKey("is_user_logged_in")
    private val userName = stringPreferencesKey("user_name")
    private val authToken = stringPreferencesKey("auth_token")

    private val userLoginFlow: Flow<Boolean> = application.dataStore.data.map {
        it[isLoggedIn] ?: false
    }

    override suspend fun getUserLoginFlow(): Flow<Boolean> = userLoginFlow

    override suspend fun saveLoginData(userData: LoginData) {
        application.dataStore.edit {
            it[userName] = userData.email
            it[authToken] = userData.authToken
            it[isLoggedIn] = true
        }
    }

    override suspend fun editLoginData(userData: LoginData) {
        application.dataStore.edit {
            it[userName] = userData.email
            it[authToken] = userData.authToken
        }
    }

    override suspend fun clearLoginData() {
        application.dataStore.edit {
            it.remove(authToken)
            it.remove(userName)
            it[isLoggedIn] = false
        }
    }

    override suspend fun getCredentialData(): UserCredentialData {
        return application.dataStore.data.first().let {
            UserCredentialData(it[userName] ?: "", it[authToken] ?: "")
        }
    }

}