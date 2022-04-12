package com.example.settings_module.color_settings.di

import android.content.Context
import androidx.room.Room
import com.example.settings_module.SettingsDataBase
import com.example.settings_module.color_settings.data.local.dao.ColorDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    fun providesSettingsDao(
        settingsDataBase: SettingsDataBase
    ):ColorDao = settingsDataBase.getColorDao()

}