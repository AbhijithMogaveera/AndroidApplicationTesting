package com.abhijith.feature_auth.data.source.remote

import com.abhijith.feature_auth.data.source.remote.model.request.LoginRequest
import com.abhijith.feature_auth.data.source.remote.model.response.RegistrationResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationApi {

    @POST("/app/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ):RegistrationResponse

}