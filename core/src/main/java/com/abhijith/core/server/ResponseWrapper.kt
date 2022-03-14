package com.abhijith.core.server

data class Response<T>(
    val isVolatile:Boolean,
    val data:T
)