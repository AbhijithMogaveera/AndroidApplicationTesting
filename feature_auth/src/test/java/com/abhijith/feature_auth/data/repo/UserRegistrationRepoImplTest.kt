package com.abhijith.feature_auth.data.repo

import arrow.core.Either
import com.abhijith.core.server.Response
import com.abhijith.feature_auth.data.source.remote.RegistrationApi
import com.abhijith.feature_auth.data.source.remote.model.request.RegistrationRequest
import com.abhijith.feature_auth.data.source.remote.model.response.RegistrationResponse
import com.abhijith.feature_auth.domain.repo.UserRegistrationRepo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock


class UserRegistrationRepoImplTest {

    val api = mock(RegistrationApi::class.java)

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {
    }

    @Test
    fun registerUser() {
        val registrationData = RegistrationRequest("abhialur8898@gmail.com", "helllo", "")
        val response = Response(false, RegistrationResponse("", ""))
        `when`(api.register(registrationData))
            .thenReturn(response)
        val userRegistrationRepo: UserRegistrationRepo = UserRegistrationRepoImpl(api)
        when (val it = userRegistrationRepo.registerUser(
            registrationData.emailId,
            registrationData.password
        )) {
            is Either.Left -> {
                assert(false)
            }
            is Either.Right -> {
                assert(it.value.email == registrationData.emailId)
            }
        }
    }

    @Test
    fun deleteAccount() {
    }
}