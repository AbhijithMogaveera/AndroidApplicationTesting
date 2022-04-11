package com.example.settings_module.color_settings.presentaion

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.settings_module.R
import com.example.settings_module.color_settings.presentaion.viewmodel.ColorSettingsViewModel

@Composable
fun ColorPicker(
    vm:ColorSettingsViewModel = viewModel()
) {
    LazyColumn(
        content = {
            items(count = 10, itemContent = { _ ->

            })
        }
    )
}

@Composable
internal fun ColorViewHolder(
    modifier:Modifier = Modifier,
    color: Color,
    isSelected:Boolean
){
    Canvas(modifier = Modifier, onDraw = {
        drawCircle(
            color = color
        )
        if(isSelected){

        }
    })
}