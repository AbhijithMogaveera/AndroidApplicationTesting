package com.example.settings_module.color_settings.domain.repo

import kotlinx.coroutines.flow.Flow

class ColorSetting(
    private val colorDB: ColorDB
) {

    val colors: Flow<List<Int>> = colorDB.getColors()

    suspend fun addColor(
        color: Int
    ) = colorDB.addColor(color = color)

    suspend fun removeColor(
        color: Int
    ) = colorDB.removeColor(color = color)
    
}