package com.example.settings_module.color_settings.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MyColor(
    @PrimaryKey
    val color:Int
)