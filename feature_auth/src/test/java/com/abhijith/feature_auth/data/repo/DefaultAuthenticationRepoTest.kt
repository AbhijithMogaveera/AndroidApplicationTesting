package com.abhijith.feature_auth.data.repo

import arrow.core.Either
import com.abhijith.feature_auth.data.source.remote.AuthenticationApi
import com.abhijith.feature_auth.domain.repo.AuthenticationRepo
import com.abhijith.feature_auth.helper.Api
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test


class DefaultAuthenticationRepoTest {
    private val api = Api.retrofit.create(AuthenticationApi::class.java)

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun login() {
        runBlocking {
            Api
                .mockWebServer
                .enqueueResponse(
                    "success/registration_success_response.json",
                    200
                )
            val credentialDataStoreRepo = TestCredentialDataStoreRepo()
            assert(!credentialDataStoreRepo.getUserLoginFlow().first())
            val authenticationRepo: DefaultAuthenticationRepo = DefaultAuthenticationRepo(
                api,
                credentialDataStoreRepo
            )
            when (val it = authenticationRepo.login("abhialur8898@gmail.com", "hello")) {
                is Either.Left -> {
                    assert(false){
                        it.value.localizedMessage?:"un know"
                    }
                }
                is Either.Right -> {
                    assert(credentialDataStoreRepo.getUserLoginFlow().first())
                }
            }
        }
    }
}