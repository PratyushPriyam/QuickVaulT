package com.assignment.quickvault

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    //-------------SQLite--------------------------
    lateinit var db: SQLiteDB
    lateinit var tableDB: SQLiteDatabase
    lateinit var cursor: Cursor
    lateinit var profileSQLiteDB: ProfileSQLiteDB
    //-----------ListView & FAB--------------------
    lateinit var listView: ListView
    lateinit var fab: FloatingActionButton
    //------------Navigation Drawer----------------
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.listView)
        drawerLayout = findViewById(R.id.drawerLayout)
        navView = findViewById(R.id.navView)
        profileSQLiteDB = ProfileSQLiteDB(this)
        var userName: String = "User"
        val database = profileSQLiteDB.writableDatabase
        val cursor: Cursor = database.rawQuery("SELECT * FROM profile ORDER BY _id DESC LIMIT 1", null)
        if(cursor.moveToFirst()) {
            userName = (cursor.getString(1))
        }

        fab = findViewById(R.id.floatingActionButton3)
        supportActionBar?.title = "Welcome, $userName"
        //supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#000080")))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        fab.setOnClickListener {
            var intent = Intent(this, ManageNotes::class.java)
            intent.putExtra("note_id", -1)
            startActivity(intent)
        }
        listView.setOnItemClickListener { parent, view, position, id ->
            var intent = Intent(this, ManageNotes::class.java)
            intent.putExtra("note_id", id)
            startActivity(intent)
        }

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.callUs -> {
                    Toast.makeText(this, "Call Us at: +91-7001067632", Toast.LENGTH_LONG).show()
                }
                R.id.mailUs -> {
                    Toast.makeText(this, "Mail Us at: \n com.example.notepad@gmail.com", Toast.LENGTH_LONG).show()
                }
                R.id.aboutUs -> {
                    val inflater: View = layoutInflater.inflate(R.layout.custom_toast, findViewById(R.id.customToastMainLayoutId))
                    val toast = Toast(this)
                    toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
                    toast.duration = Toast.LENGTH_LONG
                    toast.view = inflater
                    toast.show()
                }
            }
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.profile -> startActivity(Intent(this, UserProfile::class.java))
            R.id.web -> startActivity(Intent(this, WebSite::class.java))
        }
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { // For profile icon

        menuInflater.inflate(R.menu.profile_menu, menu)
        return true
    }

    override fun onStart() {
        super.onStart()
        db = SQLiteDB(this)
        tableDB = db.writableDatabase
        cursor = tableDB.rawQuery("select * from notes order by _id DESC", null)
        var customListAdapter = SimpleCursorAdapter(
            this,
            R.layout.list_row,
            cursor,
            arrayOf("title", "description"),
            intArrayOf(R.id.idTitle, R.id.idDesc),
            0
        )
        listView.adapter = customListAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        tableDB.close()
        cursor.close()
    }
}