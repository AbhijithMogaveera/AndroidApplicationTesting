package com.abhijith.feature_auth.di

import com.abhijith.feature_auth.data.source.remote.AuthenticationApi
import com.abhijith.feature_auth.data.source.remote.RegistrationApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun providesRegistrationApi(
        retrofit: Retrofit
    ): RegistrationApi = retrofit.create(RegistrationApi::class.java)

    @Provides
    @Singleton
    fun providesAuthenticationApi(
        retrofit: Retrofit
    ): AuthenticationApi = retrofit.create(AuthenticationApi::class.java)

}
