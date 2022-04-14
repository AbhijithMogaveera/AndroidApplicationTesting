package com.abhijith.feature_auth.di

import android.app.Application
import android.content.Context
import com.abhijith.feature_auth.data.repo.DefaultAuthenticationRepo
import com.abhijith.feature_auth.data.repo.DefaultCredentialDataStoreRepo
import com.abhijith.feature_auth.data.source.remote.AuthenticationApi
import com.abhijith.feature_auth.domain.repo.AuthenticationRepo
import com.abhijith.feature_auth.domain.repo.CredentialDataStoreRepo
import com.abhijith.feature_auth.domain.repo.UserRegistrationRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepoModule {

    @Provides
    @Singleton
    fun providesCredentialTestRepo(
        @ApplicationContext
        application: Context
    ): CredentialDataStoreRepo = DefaultCredentialDataStoreRepo(application as Application)


    @Provides
    @Singleton
    fun providesAuthenticationRepo(
        authenticationApi: AuthenticationApi,
        credentialDataStoreRepo: CredentialDataStoreRepo
    ):AuthenticationRepo = DefaultAuthenticationRepo(
        authenticationApi,
        credentialDataStoreRepo
    )

}
