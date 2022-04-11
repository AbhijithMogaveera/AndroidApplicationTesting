package com.example.settings_module.color_settings.di

import android.content.Context
import androidx.room.Room
import com.example.settings_module.SettingsDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Provides
    @Singleton
    fun providesSettingsDB(
        @ApplicationContext context: Context
    ): SettingsDataBase = Room
        .databaseBuilder(
            context,
            SettingsDataBase::class.java,
            SettingsDataBase.NAME
        ).build()

}