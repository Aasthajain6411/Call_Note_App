package com.example.callnotes

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {

    //on conflict tells us what to do when we get the same entries again
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    //i/o very heavy process. We do it on background thread hence we add suspend variable. Once added they can only be called by another suspend function or a background thread.
    suspend fun insert(note: Note)
    @Delete
    suspend fun delete(note: Note)
    @Update
    suspend fun update(note:Note)

    @Query("SELECT * FROM notes_table WHERE id= :id")
    suspend fun getValue(id: String): Note

    @Query( "Select * From notes_table order by id ASC" )
    fun getAllNotes():LiveData<List<Note>>

    //@Query("Select * from notes_table ")
}