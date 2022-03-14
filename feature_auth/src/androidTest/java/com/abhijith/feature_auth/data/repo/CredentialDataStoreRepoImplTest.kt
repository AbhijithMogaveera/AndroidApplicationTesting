package com.abhijith.feature_auth.data.repo

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.abhijith.feature_auth.domain.model.LoginData
import com.abhijith.feature_auth.domain.repo.CredentialDataStoreRepo
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class CredentialDataStoreRepoImplTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Inject
    lateinit var repo:CredentialDataStoreRepo

    @Before
    fun setUp() {
        hiltRule.inject()
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

    val authToken = "abhi8898"
    val email = "abhialur8898@gamil.com"
    val loginData = LoginData(email, authToken)

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