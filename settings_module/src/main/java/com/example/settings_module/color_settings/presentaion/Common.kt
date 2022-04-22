package com.example.settings_module.color_settings.presentaion

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

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
                drawCircle(
                    color = Color.Black,
                    radius = size.minDimension
                )
                drawCircle(
                    color = color,
                    radius = size.minDimension - 10
                )
            } else {
                drawCircle(
                    color = color,
                    radius = size.minDimension - 10
                )
            }
        })
}