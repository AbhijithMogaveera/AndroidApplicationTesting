package com.example.settings_module.color_settings.data.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.settings_module.color_settings.data.local.model.MyColor
import kotlinx.coroutines.flow.Flow

interface ColorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertColor(color: MyColor)

    @Delete()
    suspend fun deleteColor(color:MyColor)

    @Query("select * from MyColor")
    fun getAllColors(): Flow<List<MyColor>>

}