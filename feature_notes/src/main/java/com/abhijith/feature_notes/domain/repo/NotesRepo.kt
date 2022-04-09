package com.abhijith.feature_notes.domain.repo

import arrow.core.Either
import com.abhijith.feature_notes.data.local.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepo {
    suspend fun createOrUpdateNote(note: Note)
    suspend fun deleteNote(noteId: String)
    suspend fun getAllNote():Flow<List<Note>>
}