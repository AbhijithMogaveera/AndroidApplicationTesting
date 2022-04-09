package com.abhijith.feature_notes.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey
    val id: String,
    val title: String,
    val desc: String,
    val color: String
)