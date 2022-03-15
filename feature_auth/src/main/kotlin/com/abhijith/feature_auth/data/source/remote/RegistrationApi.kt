package com.abhijith.feature_auth.data.source.remote

import com.abhijith.core.server.Response
import com.abhijith.feature_auth.data.source.remote.model.request.RegistrationRequest
import com.abhijith.feature_auth.data.source.remote.model.response.RegistrationResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RegistrationApi {
    @POST("/app/register_user")
    suspend fun register(
        @Body registrationData: RegistrationRequest
    ): Response<RegistrationResponse>
}

