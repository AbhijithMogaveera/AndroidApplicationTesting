package com.example.settings_module.color_settings.domain.repo

import kotlinx.coroutines.flow.Flow

interface ColorDB {
    companion object{
        const val SUCCESS = 0
        const val UNABLE_TO_ADD_COLOR = 0
        const val UNABLE_TO_REMOVE_COLOR = 2
    }
    fun getColors(): Flow<List<Int>>
    suspend fun addColor(color:Int): Int
    suspend fun removeColor(color:Int): Int
}