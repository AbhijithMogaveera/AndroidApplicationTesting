package com.abhijith.core

import java.util.concurrent.TimeUnit

object BaseApiConfig {
    const val BASE_URL = "https://localhost:8080//"
    const val CONNECTION_TIME_OUT = 30
    const val READ_TIME_OUT = 30
    const val WRITE_TIME_OUR = 30
    val timeUnit = TimeUnit.SECONDS
}