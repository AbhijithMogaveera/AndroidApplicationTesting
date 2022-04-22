package com.example.settings_module.color_settings.presentaion.viewmodel

import androidx.annotation.ColorInt
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.graphics.toColorInt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.settings_module.color_settings.data.repo.ColorDB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ColorSettingsViewModel
@Inject constructor(
    private val colorDB: ColorDB,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    val colors = flow {
        emit(listOf(
            "#ffffff".toColorInt(),
            "#ffff4f".toColorInt()
        ))
    }
    var selectColorIndex by mutableStateOf(0)

    fun selectColor(@ColorInt color: Int) {
        viewModelScope.launch(coroutineDispatcher) {
            selectColorIndex = colors.first().indexOf(color)
        }
    }

}