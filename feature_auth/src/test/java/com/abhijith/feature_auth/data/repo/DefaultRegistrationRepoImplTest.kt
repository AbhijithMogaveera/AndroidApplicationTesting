package com.abhijith.feature_auth.data.repo

import arrow.core.Either
import com.abhijith.feature_auth.data.source.remote.RegistrationApi
import com.abhijith.feature_auth.data.source.remote.model.request.RegistrationRequest
import com.abhijith.feature_auth.domain.repo.UserRegistrationRepo
import com.abhijith.feature_auth.helper.Api
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

fun MockWebServer.enqueueResponse(fileName: String?, code: Int) {
    enqueue(
        MockResponse().apply {
            setResponseCode(code)
            fileName?.let { fileName ->
                javaClass.classLoader?.getResourceAsStream("api_response/$fileName")?.let { inputStream ->
                    setBody(inputStream.source().buffer().readString(StandardCharsets.UTF_8))
                }
            }
        }
    )
}

class DefaultRegistrationRepoImplTest {

    private val api = Api.retrofit.create(RegistrationApi::class.java)

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `registration success response`() {
        runBlocking {
            Api.mockWebServer.enqueueResponse("success/registration_success_response.json", 200)
            val registrationData = RegistrationRequest("abhialur8898@gmail.com", "helllo", "")
            val credentialDataStoreRepo = TestCredentialDataStoreRepo()
            val userRegistrationRepo: UserRegistrationRepo = DefaultRegistrationRepoImpl(
                api,
                credentialDataStoreRepo
            )
            assert(
                !credentialDataStoreRepo.getUserLoginFlow().first()
            )

            when (val it = userRegistrationRepo.registerUser(
                registrationData.emailId,
                registrationData.password
            )) {
                is Either.Left -> {
                    val value = it.value
                    if (value is HttpException) {
                        if (value.code() == 404)
                            assert(false) {
                                "resource not found " + value.response()?.errorBody()?.string()
                            }
                    }
                    assert(false) {
                        value.localizedMessage ?: "unknow"
                    }
                }
                is Either.Right -> {

                    assert(
                        it.value.email == registrationData.emailId
                    )
                    assert(
                        credentialDataStoreRepo.getUserLoginFlow().first()
                    )
                }
            }
        }
    }

    @Test
    fun `registration error response`() {
        runBlocking {
            Api.mockWebServer.enqueueResponse(null, 409)
            val registrationData = RegistrationRequest("abhialur8898@gmail.com", "helllo", "")
            val credentialDataStoreRepo = TestCredentialDataStoreRepo()
            val userRegistrationRepo: UserRegistrationRepo =
                DefaultRegistrationRepoImpl(api, credentialDataStoreRepo)
            when (val it = userRegistrationRepo.registerUser(
                registrationData.emailId,
                registrationData.password
            )) {
                is Either.Left -> {
                    val value = it.value
                    if (value is HttpException) {
                        assert(value.code() == 409)
                        assert(!credentialDataStoreRepo.getUserLoginFlow().first())
                        return@runBlocking
                    }
                    value.printStackTrace()
                    assert(false)
                }
                is Either.Right -> {
                    assert(false)
                }
            }
        }
    }

    @Test
    fun deleteAccount() {
        runBlocking {
            Api.mockWebServer.enqueueResponse(null, 409)
            val registrationData = RegistrationRequest("abhialur8898@gmail.com", "helllo", "")
            val credentialDataStoreRepo = TestCredentialDataStoreRepo()
            val userRegistrationRepo: UserRegistrationRepo =
                DefaultRegistrationRepoImpl(api, credentialDataStoreRepo)
            when (val it = userRegistrationRepo.deleteAccount(
                registrationData.emailId,
            )) {
                is Either.Left -> {

                }
                is Either.Right -> {
                    val value = it.value
                    assert(value)
                    assert(!credentialDataStoreRepo.getUserLoginFlow().first())
                }
            }
        }
    }
}