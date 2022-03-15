package com.abhijith.feature_auth.data.source.remote.model.request

data class LoginRequest(
    val emailId:String,
    val password:String,
    val deviceID:String
)