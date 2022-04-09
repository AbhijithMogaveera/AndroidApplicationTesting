package com.example.settings_module.color_settings.presentaion

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
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
