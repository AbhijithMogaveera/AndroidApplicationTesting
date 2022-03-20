package com.abhijith.feature_auth.domain.usecase

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class PasswordValidationTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testForRightInput(){
        runBlocking {
            val validator = PasswordValidation()
            assertTrue(validator.validate("Abhi8898"))
            assertFalse(validator.validate("Abhi889"))
        }
    }
}