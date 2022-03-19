package com.abhijith.feature_auth.domain.usecase

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class EmailValidationUseCaseTest{
    lateinit var emailValidationUseCase: EmailValidationUseCase
    @Before
    fun setUp(){
        emailValidationUseCase = EmailValidationUseCase()
    }

    @After
    fun tearDown() {

    }

    @Test
    fun testForWrongEMail(){
        runBlocking {
            assert(!emailValidationUseCase.flowOfValidationResults.first())
            emailValidationUseCase.validate("abhialur88988gmail.com")
            assert(!emailValidationUseCase.flowOfValidationResults.first())
            emailValidationUseCase.validate("abhialur8898\$email.com")
            assert(!emailValidationUseCase.flowOfValidationResults.first())
        }
    }

    @Test
    fun testForWrongRightEmail(){
        runBlocking {
            assert(!emailValidationUseCase.flowOfValidationResults.first())
            emailValidationUseCase.validate("abhialur88988@gmail.com")
            assert(emailValidationUseCase.flowOfValidationResults.first())
            emailValidationUseCase.validate("abhialur889s@email.com")
            assert(emailValidationUseCase.flowOfValidationResults.first())
        }
    }

}