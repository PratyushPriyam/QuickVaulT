package com.assignment.quickvault

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton

class UserProfile : AppCompatActivity() {
    lateinit var profileDB: ProfileSQLiteDB
    lateinit var db: SQLiteDatabase
    lateinit var cursor: Cursor
    lateinit var contentValues: ContentValues

    lateinit var nameTV: TextView
    lateinit var bioTV: TextView
    lateinit var ageTV: TextView
    lateinit var saveProfileDataBtn: AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        supportActionBar?.title = "User Profile"

        profileDB = ProfileSQLiteDB(this)
        db = profileDB.writableDatabase
        contentValues = ContentValues()

        nameTV = findViewById(R.id.nameTV)
        bioTV = findViewById(R.id.bioTV)
        ageTV = findViewById(R.id.ageTV)
        saveProfileDataBtn = findViewById(R.id.saveProfileDataBtn)

        cursor = db.rawQuery("SELECT * FROM profile ORDER BY _id DESC LIMIT 1", null)
        if(cursor.moveToFirst()) {
            nameTV.setText(cursor.getString(1))
            bioTV.setText(cursor.getString(2))
            ageTV.setText(cursor.getString(3))
        }

        saveProfileDataBtn.setOnClickListener {
            saveProfileData()
        }

    }
    private fun saveProfileData() {
        contentValues.put("name", nameTV.text.toString())
        contentValues.put("bio", bioTV.text.toString())
        contentValues.put("age", ageTV.text.toString())
        db.insert("profile", null, contentValues)
        startActivity(Intent(this, MainActivity::class.java))
        Toast.makeText(this, "Done!!!", Toast.LENGTH_SHORT).show()
    }
}