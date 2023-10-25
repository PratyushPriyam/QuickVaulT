package com.assignment.quickvault

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ProfileSQLiteDB(val cxt: Context): SQLiteOpenHelper(cxt, "ProfileDB", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table profile(_id integer primary key autoincrement, name text, bio text, age integer)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}