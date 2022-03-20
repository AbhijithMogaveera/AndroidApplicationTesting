package com.abhijith.feature_auth.domain.usecase

import javax.inject.Inject

class PasswordValidation
@Inject constructor() {
    suspend fun validate(string: String): Boolean {
        return (string.length >= 8)
    }
}