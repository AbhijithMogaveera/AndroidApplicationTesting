package com.abhijith.feature_notes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abhijith.feature_notes.data.local.dao.NotesDao
import com.abhijith.feature_notes.data.local.model.Note

@Database(
    entities = [Note::class],
    version = 1,
)
abstract class NotesDB:RoomDatabase() {
    abstract fun getNotesDao():NotesDao
    companion object{
        const val NAME = "notes-data-base"
    }
}