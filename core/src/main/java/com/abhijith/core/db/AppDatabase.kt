package com.abhijith.core.db

import androidx.room.Database
import androidx.room.RoomDatabase

abstract class AppDatabase:RoomDatabase() {
    companion object{
        const val DB_NAME = "my_app_db"
    }
}