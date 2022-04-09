package com.abhijith.feature_notes.presentaion.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhijith.core.util.getContrastColor
import com.abhijith.feature_notes.presentaion.util.CardConfig

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteViewHolder(
    modifier: Modifier = Modifier,
    cardConfig: CardConfig = CardConfig()
) {
    val color = getContrastColor(cardConfig.color.toArgb())
    Card(
        backgroundColor = cardConfig.color,
        modifier = modifier,
        onClick = {},
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, color = Color(color))
    ) {
        Column {
            Text(
                text = "Hii",
                color = Color(color),
                modifier = Modifier.padding(10.dp),
                style = TextStyle(fontWeight = FontWeight(800)),
                fontSize = 19.sp
            )
            Text(
                text = "Hii hello how are you just checking whats happening",
                color = Color(color),
                modifier = Modifier.padding(start = 10.dp, top = 0.dp, bottom = 10.dp, end = 10.dp)
            )
        }
    }
}

@Preview
@Composable
fun NoteViewHolderPreview() {
    Column {
        val modifier = Modifier.padding(10.dp)
        NoteViewHolder(modifier = modifier, CardConfig(Color.Black))
        NoteViewHolder(modifier = modifier, CardConfig(Color.Blue))
        NoteViewHolder(modifier = modifier, CardConfig(Color.White))
        NoteViewHolder(modifier = modifier, CardConfig(Color.Cyan))
        NoteViewHolder(modifier = modifier, CardConfig(Color.Magenta))
        NoteViewHolder(modifier = modifier, CardConfig(Color.Black))
        NoteViewHolder(modifier = modifier, CardConfig(Color.Blue))
        NoteViewHolder(modifier = modifier, CardConfig(Color.White))
        NoteViewHolder(modifier = modifier, CardConfig(Color.Cyan))
        NoteViewHolder(modifier = modifier, CardConfig(Color.Magenta))
    }
}