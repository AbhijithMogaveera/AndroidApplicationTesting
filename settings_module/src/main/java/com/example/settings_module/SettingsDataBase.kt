package com.example.settings_module

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.settings_module.color_settings.data.dao.ColorDao
import com.example.settings_module.color_settings.data.model.MyColor

@Database(
    entities = [
        MyColor::class
    ],
    version = 1,
)
abstract class SettingsDataBase:RoomDatabase() {
    abstract fun getColorDao():ColorDao

    companion object{
        const val NAME = "settings_database"
    }
}