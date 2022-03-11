package com.abhijith.feature_auth.data.repo

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.abhijith.feature_auth.domain.model.LoginData
import com.abhijith.feature_auth.domain.repo.CredentialDataStoreRepo
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CredentialDataStoreRepoImplTest {

    private lateinit var application: Application
    private lateinit var repo:CredentialDataStoreRepo
    @Before
    fun setUp() {
        application = ApplicationProvider.getApplicationContext()
        repo = CredentialDataStoreRepoImpl(application)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun getUserLoginFlow() {

    }

    @Test
    fun saveLoginData() {
        runBlocking {
            clearData()
            val authToken = "abhi8898"
            val email = "abhialur8898@gamil.com"

            repo.getCredentialData().apply {
                assertTrue(this.authToken != authToken)
                assertTrue(this.userName != email)
            }
            saveData(email, authToken)
            repo.getCredentialData().apply {
                assertTrue(this.authToken == authToken)
                assertTrue(this.userName == email)
            }
        }
    }

    private suspend fun clearData() {
        repo.clearLoginData()
    }

    private suspend fun saveData(email: String, authToken: String) {
        repo.saveLoginData(
            LoginData(
                email,
                authToken
            )
        )
    }

    @Test
    fun editLoginData() {

    }

    @Test
    fun clearLoginData() {

    }

    @Test
    fun getCredentialData() {

    }
}