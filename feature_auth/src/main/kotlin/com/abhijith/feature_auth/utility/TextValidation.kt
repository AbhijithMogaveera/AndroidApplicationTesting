package com.abhijith.feature_auth.utility

import android.util.Patterns
import java.util.regex.Pattern

private const val regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"

fun String.validateForEmail() = this.isNotBlank() && Pattern.compile(regexPattern).matcher(this).matches()
