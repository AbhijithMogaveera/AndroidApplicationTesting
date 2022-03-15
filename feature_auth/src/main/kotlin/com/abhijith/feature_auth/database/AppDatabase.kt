package com.abhijith.feature_auth.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abhijith.core.db.AppDatabase

/*@Database(
    entities = [],
    exportSchema = false,
    version = 1
)*/
abstract class AuthDataBase : RoomDatabase(

), AppDatabase {

}