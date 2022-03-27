package com.abhijith.feature_auth.di

import android.app.Application
import android.content.Context
import com.abhijith.feature_auth.data.repo.DefaultCredentialDataStoreRepo
import com.abhijith.feature_auth.domain.repo.AuthenticationRepo
import com.abhijith.feature_auth.domain.repo.CredentialDataStoreRepo
import com.androidTest.mocks.data.repo.fake.TestAuthenticationRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepoModule::class]
)
@Module
object RepoModuleTest{

    @Provides
    @Singleton
    fun providesAuthenticationRepo(): AuthenticationRepo = TestAuthenticationRepo.getInstance()

    @Provides
    @Singleton
    fun provideAppLevelDispatcher() = Dispatchers.IO

    @Provides
    @Singleton
    fun providesCredentialTestRepo(
        @ApplicationContext
        application: Context
    ): CredentialDataStoreRepo = DefaultCredentialDataStoreRepo(application as Application)

}