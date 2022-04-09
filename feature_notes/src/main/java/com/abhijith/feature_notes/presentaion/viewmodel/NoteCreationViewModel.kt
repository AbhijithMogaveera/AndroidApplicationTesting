package com.abhijith.feature_notes.presentaion.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhijith.core.utility.ScreenState
import com.abhijith.feature_notes.domain.repo.NotesRepo
import com.abhijith.feature_notes.presentaion.util.ArgsKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.IllegalStateException
import javax.inject.Inject

@HiltViewModel
class NoteCreationViewModel
@Inject constructor(
    private val notesRepo: NotesRepo,
    private val savedInsHandle: SavedStateHandle
) : ViewModel() {

    private val _screenState: MutableStateFlow<ScreenState> = MutableStateFlow(ScreenState.CREATION)
    val screenState = _screenState.asStateFlow()

    private var _title: String = ""
    private var _note: String = ""

    val titleState by mutableStateOf(_title)
    val bodyState by mutableStateOf(_note)

    private lateinit var noteId:String

    init {
        viewModelScope.launch {
            val isNew = savedInsHandle[ArgsKey.CREATE_NEW] ?: true
            if (isNew) {
                _screenState.emit(ScreenState.CREATION)
                _title = savedInsHandle[ArgsKey.NOTE_TITLE] ?: ""
                _note = savedInsHandle[ArgsKey.NOTE_TITLE] ?: ""
            } else {
                _screenState.emit(ScreenState.VIEW)
                noteId = savedInsHandle[ArgsKey.NOTE_ID] ?: throw IllegalStateException()
            }

        }
    }

    fun openForEdit() {
        viewModelScope.launch {
            _screenState.emit(ScreenState.EDIT)
        }
    }

    fun updateTitle(
        title: String
    ) {
        _title = title
    }

    fun updateBody(
        body: String
    ) {
        _note = body
    }

    fun delete() {
        viewModelScope.launch {
            notesRepo.deleteNote(noteId = noteId)
        }
    }
}