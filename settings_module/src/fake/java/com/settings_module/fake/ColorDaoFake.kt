package com.settings_module.fake

import com.example.settings_module.color_settings.data.local.dao.ColorDao
import com.example.settings_module.color_settings.data.local.model.MyColor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class ColorDaoFake:ColorDao {

    private val flow = MutableStateFlow<List<MyColor>>(listOf())
    private val list:MutableList<MyColor> = mutableListOf()

    override suspend fun insertColor(color: MyColor) {
        list.add(color)
        flow.emit(list)
    }

    override suspend fun deleteColor(color: MyColor) {
        list.remove(color)
        flow.emit(list)
    }

    override fun getAllColors(): Flow<List<MyColor>> = flow
}