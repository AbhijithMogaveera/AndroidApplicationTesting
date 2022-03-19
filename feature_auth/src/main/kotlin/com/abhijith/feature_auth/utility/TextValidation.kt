package com.abhijith.feature_auth.utility

import java.util.regex.Pattern

private const val emailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"
private const val alphaNumericPatter = "^[a-zA-Z0-9]*\$"

fun String.validateForEmail() = this.isNotBlank() && Pattern.compile(emailPattern).matcher(this).matches()
fun String.validateForAlphaNumeric() = this.isNotBlank() && Pattern.compile(alphaNumericPatter).matcher(this).matches()
