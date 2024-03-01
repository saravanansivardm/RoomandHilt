package com.example.noteapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.repositry.NoteRepository
import com.example.noteapp.roomDB.entity.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _noteList = MutableStateFlow<List<Note>>(emptyList())
    val noteList = _noteList.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _doesValueContain = MutableStateFlow(false)
    val doesValueContain = _doesValueContain.asStateFlow()


    private val _persons = noteList
    val persons = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_persons) { text, persons ->
            if (text.isBlank()) {
                persons
            } else {
                delay(2000L)
                persons.filter {
                    Log.e("ll_log", it.doesMatchSearchQuery(text).toString())
                    _doesValueContain.value = it.doesMatchSearchQuery(text)
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _persons.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    init {
        viewModelScope.launch {
            repository.getAllNote().distinctUntilChanged().collect() { listOfNotes ->
                if (listOfNotes.isEmpty()) {
                    Log.d("Empty", ": Empty list")
                } else {
                    _noteList.value = listOfNotes
                }
            }
        }
    }

    fun addNote(note: Note) = viewModelScope.launch { repository.addNote(note) }
    fun removeNote(note: Note) = viewModelScope.launch { repository.deleteNote(note) }
    fun deleteAllNote(note: Note) = viewModelScope.launch { repository.deleteAllNotes(note) }
}