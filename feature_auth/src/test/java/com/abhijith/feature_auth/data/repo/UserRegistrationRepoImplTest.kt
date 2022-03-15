package com.abhijith.feature_auth.data.repo

import arrow.core.Either
import com.abhijith.core.server.Response
import com.abhijith.feature_auth.data.source.remote.RegistrationApi
import com.abhijith.feature_auth.data.source.remote.model.request.RegistrationRequest
import com.abhijith.feature_auth.data.source.remote.model.response.RegistrationResponse
import com.abhijith.feature_auth.domain.repo.UserRegistrationRepo
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

fun MockWebServer.enqueueResponse(fileName: String, code: Int) {
    val inputStream = javaClass.classLoader?.getResourceAsStream("api_response/$fileName")
    val source = inputStream?.let { inputStream.source().buffer() }
    source?.let {
        enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody(source.readString(StandardCharsets.UTF_8))
        )
    }
}

class UserRegistrationRepoImplTest {
    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    var  mockWebServer = MockWebServer()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RegistrationApi::class.java)
//    val api = mock(RegistrationApi::class.java)

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }


    @Test
    fun registerUser() {
        runBlocking {
            mockWebServer.enqueueResponse("shops_reponse.json",200)
            val registrationData = RegistrationRequest("abhialur8898@gmail.com", "helllo", "")
            /*val response = Response(false, RegistrationResponse("", ""))*/
            /* `when`(api.register(registrationData)).thenReturn(response)*/
            val userRegistrationRepo: UserRegistrationRepo = UserRegistrationRepoImpl(api)
            when (val it = userRegistrationRepo.registerUser(
                registrationData.emailId,
                registrationData.password
            )) {
                is Either.Left -> {
                    val value = it.value
                    if(value is HttpException){
                        if(value.code() == 404)
                            assert(false){
                                "resource not found "+value.response()?.errorBody()?.string()
                            }
                    }
                    assert(false){
                        value.localizedMessage?:"unknow"
                    }
                }
                is Either.Right -> {
                    assert(
                        it.value.email == registrationData.emailId
                    )
                }
            }
        }

    }

    @Test
    fun deleteAccount() {
    }
}