package com.abhijith.feature_notes.di

import android.content.Context
import androidx.room.Room
import com.abhijith.feature_notes.data.local.NotesDB
import com.abhijith.feature_notes.data.local.dao.NotesDao
import com.abhijith.feature_notes.data.repo.NotesRepoImpl
import com.abhijith.feature_notes.domain.repo.NotesRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesRoomDB(
        @ApplicationContext context: Context
    ):NotesDB = Room.databaseBuilder(
        context,
        NotesDB::class.java,
        NotesDB.NAME
    ).build()


    @Provides
    fun providesNotesDao(
        notesDB: NotesDB
    ):NotesDao = notesDB.getNotesDao()

    @Provides
    fun provideNoteRepo(
        notesDao: NotesDao
    ):NotesRepo = NotesRepoImpl(notesDao = notesDao)

}