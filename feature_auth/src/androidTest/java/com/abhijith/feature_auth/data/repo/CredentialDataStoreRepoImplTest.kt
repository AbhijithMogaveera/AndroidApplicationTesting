package com.abhijith.feature_auth.data.repo

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.abhijith.feature_auth.domain.model.LoginData
import com.abhijith.feature_auth.domain.repo.CredentialDataStoreRepo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CredentialDataStoreRepoImplTest {

    val authToken = "abhi8898"
    val email = "abhialur8898@gamil.com"

    val loginData = LoginData(email, authToken)

    private var application: Application = ApplicationProvider.getApplicationContext()
    private var repo:CredentialDataStoreRepo = CredentialDataStoreRepoImpl(application)

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
        runBlocking {
            clearData()
        }
    }

    @Test
    fun getUserLoginFlow() {

    }

    @Test
    fun saveLoginData() {
        runBlocking {
            val authToken = "abhi8898"
            val email = "abhialur8898@gamil.com"

            repo.getCredentialData().apply {
                assertTrue(this.authToken != authToken)
                assertTrue(this.userName != email)
            }
            saveData(loginData)
            repo.getCredentialData().apply {
                assertTrue(this.authToken == authToken)
                assertTrue(this.userName == email)
            }
        }
    }

    @Test
    fun editLoginData() {
        runBlocking { 
            val email1 = "newEmail@test.com"
            repo.editLoginData(loginData.copy(email = email1))
            assertTrue(repo.getCredentialData().userName == email1)
        }
    }

    @Test
    fun clearLoginData() {

    }

    @Test
    fun getCredentialData() {

    }

    @Test
    fun login_flow_test(){
        runBlocking {
            launch {
                assertFalse(repo.getUserLoginFlow().first())
                repo.saveLoginData(loginData)
                assertTrue(repo.getUserLoginFlow().first())
                repo.clearLoginData()
                assertFalse(repo.getUserLoginFlow().first())
            }
        }
    }

    private suspend fun clearData() {
        repo.clearLoginData()
    }

    private suspend fun saveData(email: LoginData) {
        repo.saveLoginData(email)
    }
}