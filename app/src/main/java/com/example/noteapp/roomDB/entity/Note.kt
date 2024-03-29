package com.example.noteapp.roomDB.entity


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "notes_tbl")
class Note @RequiresApi(Build.VERSION_CODES.O) constructor(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),

    @ColumnInfo(name = "last_migration_title")
    val title: String,

    @ColumnInfo(name = "last_migration_description")
    val description: String,

//    @ColumnInfo(name = "note_entry_date")
//    val entryDate: Date = Date.from(Instant.now())
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$title$description",
            "$title $description",
            "${title.first()} ${description.first()}",
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}