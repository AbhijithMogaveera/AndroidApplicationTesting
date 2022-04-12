package com.example.settings_module.color_settings.data.local.dao

import androidx.room.*
import com.example.settings_module.color_settings.data.local.model.MyColor
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertColor(color: MyColor)

    @Delete()
    suspend fun deleteColor(color:MyColor)

    @Query("select * from MyColor")
    fun getAllColors(): Flow<List<MyColor>>

}