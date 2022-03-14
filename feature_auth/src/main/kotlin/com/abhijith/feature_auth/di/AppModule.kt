package com.abhijith.feature_auth.di

import android.app.Application
import android.content.Context
import com.abhijith.core.BaseApiConfig
import com.abhijith.feature_auth.data.repo.CredentialDataStoreRepoImpl
import com.abhijith.feature_auth.data.source.remote.RegistrationApi
import com.abhijith.feature_auth.domain.repo.CredentialDataStoreRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun providesCredentialTestRepo(
        @ApplicationContext application: Context
    ): CredentialDataStoreRepo = CredentialDataStoreRepoImpl(application as Application)

}