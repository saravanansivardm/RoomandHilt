package com.example.noteapp.repositry

import com.example.noteapp.roomDB.dao.NoteDatabaseDao
import com.example.noteapp.roomDB.entity.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDatabaseDao) {
    suspend fun addNote(note: Note) = noteDao.insert(note)
    suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)
    suspend fun updateNote(note: Note) = noteDao.update(note)
    suspend fun getAllNote(): Flow<List<Note>> = noteDao.getAllNotes().flowOn(Dispatchers.IO).conflate()
    suspend fun getNoteById(id: String) = noteDao.getNoteById(id)
    suspend fun deleteAllNotes(note: Note) = noteDao.deleteAllNotes()
}