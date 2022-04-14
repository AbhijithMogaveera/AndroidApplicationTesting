package com.example.settings_module.color_settings.data.repo

import androidx.annotation.ColorInt
import com.example.settings_module.color_settings.data.local.dao.ColorDao
import com.example.settings_module.color_settings.data.local.model.MyColor
import com.example.settings_module.color_settings.domain.repo.ColorDB
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ColorDB @Inject constructor(
    private val colorDao: ColorDao
):ColorDB {

    override fun getColors(): Flow<List<Int>> = colorDao.getAllColors().map { myColorList ->
        myColorList.map { myColor->
            myColor.color
        }
    }

    override suspend fun addColor(@ColorInt color: Int): Int {
        val it = runCatching {
            colorDao.insertColor(MyColor(color))
        }
        return when(it.isSuccess){
            true -> ColorDB.SUCCESS
            false -> ColorDB.UNABLE_TO_ADD_COLOR
        }
    }

    override suspend fun removeColor(color: Int): Int {
        val it = runCatching {
            colorDao.deleteColor(MyColor(color))
        }
        return when(it.isSuccess){
            true -> ColorDB.SUCCESS
            false -> ColorDB.UNABLE_TO_REMOVE_COLOR
        }
    }
}