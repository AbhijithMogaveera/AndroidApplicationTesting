package com.abhijith.feature_notes.data.repo

import com.abhijith.feature_notes.data.local.dao.NotesDao
import com.abhijith.feature_notes.data.local.model.Note
import com.abhijith.feature_notes.domain.repo.NotesRepo
import kotlinx.coroutines.flow.Flow

class NotesRepoImpl(
    private val notesDao: NotesDao
) : NotesRepo {

    override suspend fun createOrUpdateNote(note: Note) {
        notesDao.upsert(note = note)
    }

    override suspend fun deleteNote(noteId: String) {
        notesDao.delete(noteID = noteId)
    }

    override suspend fun getAllNote(): Flow<List<Note>> = notesDao.getAllNotes()

}