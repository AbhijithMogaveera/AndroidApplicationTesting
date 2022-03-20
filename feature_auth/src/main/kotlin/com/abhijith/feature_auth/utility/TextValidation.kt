package com.abhijith.feature_auth.utility

import java.util.regex.Pattern

private const val emailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"
private const val passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"

fun String.validateForEmail() = this.isNotBlank() && Pattern.compile(emailPattern).matcher(this).matches()
fun String.validateForPassword() = this.isNotBlank() && Pattern.compile(passwordRegex).matcher(this).matches()
