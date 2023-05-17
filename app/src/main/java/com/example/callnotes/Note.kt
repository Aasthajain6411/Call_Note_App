package com.example.callnotes

import android.annotation.SuppressLint
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//To make a normal class an entity add this
//this has created a table in SQLite and we have to tell the name of the table
@Entity(tableName = "notes_table")
class Note(@ColumnInfo(name="text") val text: String?, @ColumnInfo(name="Discussion") val discuss: String?,
           @PrimaryKey @ColumnInfo(name="id") var id:String) {
    //so that the primary key keeps incrementing on its own

}