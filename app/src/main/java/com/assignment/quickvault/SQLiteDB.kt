package com.assignment.quickvault

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteDB(val context: Context): SQLiteOpenHelper(context, "NotesDB", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table notes(_id integer primary key autoincrement, title text, description text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}