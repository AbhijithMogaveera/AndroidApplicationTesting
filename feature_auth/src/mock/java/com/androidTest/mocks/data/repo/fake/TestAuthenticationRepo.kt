package com.androidTest.mocks.data.repo.fake

import arrow.core.Either
import com.abhijith.feature_auth.domain.repo.AuthenticationRepo

class TestAuthenticationRepo : AuthenticationRepo {
    companion object {
        private lateinit var testRepo: TestAuthenticationRepo
        val valid_data = "abhialur8898@gmail.com" to "test1234"
        val in_valid_data = "abhialur8898@gmail.com" to "test1df234"

        /*hilt wont support dependency injection in JVM testing*/
        fun getInstance() = if (!this::testRepo.isInitialized) {
            testRepo = TestAuthenticationRepo()
            testRepo
        } else {
            testRepo
        }

    }

    override suspend fun login(userId: String, password: String): Either<Throwable, Unit> =
        Either.catch {
            if (userId != valid_data.first || password != valid_data.second) {
                throw Exception()
            }
            print("Valid data")
        }
}