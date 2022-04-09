package com.example.settings_module.color_settings.data.dao

import android.graphics.Color
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.settings_module.color_settings.data.model.MyColor

interface ColorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertColor(color: MyColor)

    @Delete()
    suspend fun deleteColor(color:MyColor)

}