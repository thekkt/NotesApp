package com.example.notes

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.notes.data.Notes
import com.example.notes.fragments.DetailsFragment
import com.example.notes.fragments.MainFragment
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var toggle : ActionBarDrawerToggle
    private lateinit var navigationView : NavigationView

    private val gson = Gson()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawer = findViewById<DrawerLayout>(R.id.drawer)

        navigationView = findViewById(R.id.navigation)

        val sharedPreferences = getSharedPreferences("list", Context.MODE_PRIVATE)

        if (sharedPreferences?.getString("list", null) != null) {
            val listType = object : TypeToken<MutableList<Notes>>() {}.type
            val json = sharedPreferences.getString("list", null)
            val userNotes: MutableList<Notes> = gson.fromJson(json, listType)
            Utils.notesList = userNotes
        }



        toggle = ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.exit -> exitProcess(0)
            }
            true
        }


        val fragmentMain = MainFragment()
        DetailsFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, fragmentMain)
            commit()
        }
        setSharedPreferences(Utils.notesList)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    private fun setSharedPreferences(userNotes: MutableList<Notes>) {
        val sharedPreference = getSharedPreferences("notesList", Context.MODE_PRIVATE)
        val editor = sharedPreference?.edit()
        val userNotesString = gson.toJson(userNotes)
        editor?.putString("notesList", userNotesString)
        editor?.apply()
    }

    private fun convertDate(pickedDate: String): Date {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        return sdf.parse(pickedDate) as Date
    }
}