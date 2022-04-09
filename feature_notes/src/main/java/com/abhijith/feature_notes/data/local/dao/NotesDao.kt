package com.abhijith.feature_notes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhijith.feature_notes.data.local.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    /** Crete or update the Existing notes */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(note: Note)

    @Query("delete from Note where id = :noteID")
    fun delete(noteID: String)

    @Query("select * from Note")
    fun getAllNotes(): Flow<List<Note>>
}