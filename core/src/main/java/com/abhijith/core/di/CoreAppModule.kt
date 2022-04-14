package com.abhijith.core.di

import com.abhijith.core.BaseApiConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CoreAppModule {

    @Provides @Singleton
    fun providesOkHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(BaseApiConfig.CONNECTION_TIME_OUT.toLong(), BaseApiConfig.timeUnit)
        readTimeout(BaseApiConfig.READ_TIME_OUT.toLong(), BaseApiConfig.timeUnit)
        writeTimeout(BaseApiConfig.WRITE_TIME_OUR.toLong(), BaseApiConfig.timeUnit)
    }.build()

    @Provides @Singleton
    fun providesRetroFit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder().apply {
        baseUrl(BaseApiConfig.BASE_URL)
        client(okHttpClient)
    }.build()

    @Provides @Singleton
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}