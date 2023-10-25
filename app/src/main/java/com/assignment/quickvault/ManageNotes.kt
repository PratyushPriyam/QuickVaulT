package com.assignment.quickvault

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton

class ManageNotes : AppCompatActivity() {
    lateinit var db: SQLiteDB
    lateinit var tableDB: SQLiteDatabase
    lateinit var cursor: Cursor
    lateinit var contentValue: ContentValues
    var note_id: Long = 0
    lateinit var txtTitle: EditText
    lateinit var txtDescription: EditText
    lateinit var saveNoteButton: AppCompatButton
    lateinit var deleteNoteButton: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_notes)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#000080")))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Manage My Notes")
        note_id = intent.getLongExtra("note_id", -1)
        txtTitle = findViewById(R.id.txtTitle)
        txtDescription = findViewById(R.id.txtDesc)
        db = SQLiteDB(this)
        tableDB = db.writableDatabase
        contentValue = ContentValues()

        saveNoteButton = findViewById(R.id.saveButton)
        deleteNoteButton = findViewById(R.id.deleteButton)


        saveNoteButton.setOnClickListener { saveNote() }
        deleteNoteButton.setOnClickListener { deleteNote() }

        if(note_id > 0) {
            tableDB = db.readableDatabase
            cursor = tableDB.query("notes", arrayOf("title", "description"), "_id=?", arrayOf(note_id.toString()), null, null, null)
            if(cursor.moveToFirst()) {
                txtTitle.setText(cursor.getString(0))
                txtDescription.setText(cursor.getString(1))
            }
        }


    }


    private fun deleteNote() {
        tableDB.delete("notes", "_id=?", arrayOf(note_id.toString()))
//        txtTitle.setText("")
//        txtDescription.setText("")
//        txtTitle.requestFocus()
        startActivity(Intent(this, MainActivity::class.java))
        Toast.makeText(this, "Deletion Successful", Toast.LENGTH_SHORT).show()
    }

    private fun saveNote() {
        if(note_id.toInt() == -1) { // New Save
            contentValue.put("title", txtTitle.text.toString())
            contentValue.put("description", txtDescription.text.toString())
            tableDB.insert("notes", null, contentValue)
//            txtTitle.setText("")
//            txtDescription.setText("")
//            txtTitle.requestFocus()
            startActivity(Intent(this, MainActivity::class.java))
            Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show()
        }
        if(note_id > 0) { // Update
            contentValue.put("title", txtTitle.text.toString())
            contentValue.put("description", txtDescription.text.toString())
            tableDB.update("notes", contentValue, "_id=?", arrayOf(note_id.toString()))
//            txtTitle.setText("")
//            txtDescription.setText("")
//            txtTitle.requestFocus()
            startActivity(Intent(this, MainActivity::class.java))
            Toast.makeText(this, "Updation Successful", Toast.LENGTH_SHORT).show()
            contentValue.clear()
        }
    }
}