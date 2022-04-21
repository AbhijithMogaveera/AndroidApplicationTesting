package com.example.settings_module.color_settings.presentaion

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.settings_module.color_settings.presentaion.viewmodel.ColorSettingsViewModel

@Composable
@Preview
fun ColorPicker(
    vm: ColorSettingsViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Green)
    ) {
        val list: List<Int> by vm.colors.collectAsState(
            initial = listOf(
                "#ffffff".toColorInt(),
                "#ffffff".toColorInt()
            )
        )
        LazyRow(
            content = {
                items(
                    count = list.size,
                    itemContent = { position ->
                        ColorViewHolder(

                            modifier = Modifier
                                .size(50.dp)
                                .padding(10.dp),

                            Color(list[position]),

                            isSelected = vm.selectColorIndex == position,

                            onClick = {
                                vm.selectColor(list[position])
                            }
                        )
                    },
                )
            }
        )
    }
}

@Composable
internal fun ColorViewHolder(
    modifier: Modifier = Modifier,
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit = {}
) {
    Canvas(
        modifier = modifier.clickable(onClick = onClick),
        onDraw = {
            if (isSelected) {

            }
            drawCircle(
                color = color
            )
        })
}


@Composable
internal fun ColorCreationPage(
    vm: ColorSettingsViewModel = viewModel()
) {

}