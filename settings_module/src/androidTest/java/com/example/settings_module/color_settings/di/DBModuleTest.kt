package com.example.settings_module.color_settings.di

import android.content.Context
import androidx.room.Room
import com.example.settings_module.SettingsDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import org.junit.Assert.*
import javax.inject.Singleton


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DBModule::class]
)
object DBModuleTest {

    @Provides @Singleton
    fun providesSettingsDB(
        @ApplicationContext
        context: Context
    ): SettingsDataBase =
        Room.inMemoryDatabaseBuilder(
            context,
            SettingsDataBase::class.java
        ).build()

}