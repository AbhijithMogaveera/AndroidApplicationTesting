package com.abhijith.feature_auth.data.source.remote

import com.abhijith.core.server.Response
import com.abhijith.feature_auth.data.source.remote.model.request.RegistrationRequest
import com.abhijith.feature_auth.data.source.remote.model.response.RegistrationResponse

interface RegistrationApi {
    fun register(registrationData: RegistrationRequest): Response<RegistrationResponse>
}

