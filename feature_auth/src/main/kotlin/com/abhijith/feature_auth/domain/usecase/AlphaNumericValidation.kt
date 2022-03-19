package com.abhijith.feature_auth.domain.usecase

import com.abhijith.feature_auth.utility.validateForAlphaNumeric
import com.abhijith.feature_auth.utility.validateForEmail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class AlphaNumericValidation
@Inject constructor(

) {
    suspend fun validate(string: String):Boolean = string.validateForAlphaNumeric()
}