package com.example.noteapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.noteapp.screen.NoteAppHomeScreen
import com.example.noteapp.ui.theme.NoteAppTheme
import com.example.noteapp.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppTheme {
                val notesViewModel: NoteViewModel by viewModels()
                NotesApp(notesViewModel)
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotesApp(noteViewModel: NoteViewModel) {
    val notesList = noteViewModel.noteList.collectAsState().value
    val searchText = noteViewModel.searchText.collectAsState().value
    val persons = noteViewModel.persons.collectAsState().value
    val doesValueContain = noteViewModel.doesValueContain.collectAsState().value
    val isSearching = noteViewModel.isSearching.collectAsState().value

    NoteAppHomeScreen(
        notes = notesList,
        persons = persons,
        doesValueContain = doesValueContain,
        onRemoveNote = { noteViewModel.removeNote(it) },
        onAddNote = { noteViewModel.addNote(it) },
        searchText=searchText,
        isSearching=isSearching,
        onSearchTextChange= { noteViewModel.onSearchTextChange(it) },
    )
}

