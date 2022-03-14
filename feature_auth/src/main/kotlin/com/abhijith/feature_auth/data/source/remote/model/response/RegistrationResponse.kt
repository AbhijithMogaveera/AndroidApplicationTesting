package com.abhijith.feature_auth.data.source.remote.model.response

data class RegistrationResponse(
    val authToke:String,
    val verificationStatus:String
)