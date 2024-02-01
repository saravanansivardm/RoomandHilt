package com.example.noteapp.screen

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteapp.R
import com.example.noteapp.components.NotesButton
import com.example.noteapp.components.NotesInputText
import com.example.noteapp.roomDB.entity.Note

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteAppHomeScreen(
    notes: List<Note>,
    onAddNote: (Note) -> Unit,
    onRemoveNote: (Note) -> Unit,
) {
    val title = remember {
        mutableStateOf("")
    }
    val description = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        Surface(
            shadowElevation = 10.dp,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) },
                actions = {
                    Icon(imageVector = Icons.Rounded.Notifications, contentDescription = "Icon")
                })
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NotesInputText(
                onTextChange = {
                    title.value = it
                },
                text = title.value,
                label = "Title",
                isSingleLine = true,
                maxLine = 1,
            )
            NotesInputText(onTextChange = {
                description.value = it
            }, text = description.value, label = "Description", isSingleLine = true, maxLine = 1)

            NotesButton(text = "Submit", isEnabled = true, onClicked = {
                if (title.value.isNotEmpty() && description.value.isNotEmpty()) {
                    onAddNote(Note(title = title.value, description = description.value))

                    title.value = ""
                    description.value = ""
                    Toast.makeText(context, "Note Added Successfully", Toast.LENGTH_SHORT).show()
                    Log.e("title_log", title.value)
                    Log.e("description_log", description.value)
                }
            })
            Spacer(modifier = Modifier.padding(top = 16.dp))
            LazyColumn {
                items(notes) { note ->
                    NoteRow(note = note,
                        onNoteClicked = { onRemoveNote(it) })
                }
            }
        }
    }
}

@Composable
fun NoteRow(
    note: Note,
    onNoteClicked: (Note) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp)
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Gray.copy(0.1f)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                .clickable { onNoteClicked(note) },
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                text = note.title, style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                text = note.description, style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp),
                text = "", style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
            )
        }
    }
}
