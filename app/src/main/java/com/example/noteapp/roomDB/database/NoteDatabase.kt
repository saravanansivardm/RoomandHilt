package com.example.noteapp.roomDB.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.example.noteapp.roomDB.dao.NoteDatabaseDao
import com.example.noteapp.roomDB.entity.Note


@Database(
    entities = [Note::class],
    version = 7,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(
            from = 6,
            to = 7,
            spec = NoteDatabase.Migration6To7::class
        ),
        AutoMigration(
            from = 5,
            to = 6,
            spec = NoteDatabase.Migration5To6::class
        ),
        AutoMigration(
            from = 4,
            to = 5,
            spec = NoteDatabase.Migration4To5::class
        ),
        AutoMigration(
            from = 3,
            to = 4,
            spec = NoteDatabase.Migration3To4::class
        ),
        AutoMigration(
            from = 2,
            to = 3,
            spec = NoteDatabase.Migration2To3::class
        ),
        AutoMigration(
            from = 1,
            to = 2,
            spec = NoteDatabase.Migration1To2::class
        ),
    ]
)
abstract class NoteDatabase : RoomDatabase() {
    @RenameColumn(
        tableName = "notes_tbl",
        fromColumnName = "note_description",
        toColumnName = "migration_description",
    )
    class Migration1To2 : AutoMigrationSpec

    @RenameColumn(
        tableName = "notes_tbl",
        fromColumnName = "note_title",
        toColumnName = "migration_title"
    )
    class Migration2To3 : AutoMigrationSpec

    @RenameColumn(
        tableName = "notes_tbl",
        fromColumnName = "migration_description",
        toColumnName = "migration_new_description",
    )
    class Migration3To4 : AutoMigrationSpec

    @RenameColumn(
        tableName = "notes_tbl",
        fromColumnName = "migration_title",
        toColumnName = "migration_new_title",
    )
    class Migration4To5 : AutoMigrationSpec

    @RenameColumn(
        tableName = "notes_tbl",
        fromColumnName = "migration_new_description",
        toColumnName = "last_migration_description",
    )
    class Migration5To6 : AutoMigrationSpec

    @RenameColumn(
        tableName = "notes_tbl",
        fromColumnName = "migration_new_title",
        toColumnName = "last_migration_title",
    )
    class Migration6To7 : AutoMigrationSpec


    abstract fun noteDao(): NoteDatabaseDao
}