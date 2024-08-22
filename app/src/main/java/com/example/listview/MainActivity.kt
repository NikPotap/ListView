package com.example.listview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import android.widget.ArrayAdapter as ArrayAdapter
import android.content.Context
import android.content.Intent
import android.widget.AdapterView
import androidx.activity.enableEdgeToEdge

class MainActivity : AppCompatActivity() {
    private lateinit var toolbarMain: Toolbar
    private lateinit var inputNameET: EditText
    private lateinit var inputAgeET: EditText
    private lateinit var saveBTN: Button
    private lateinit var usersViewLV: ListView
    private val usersList: MutableList<User> = mutableListOf()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbarMain = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbarMain)
        toolbarMain.title = resources.getString(R.string.app_name)

        inputNameET = findViewById(R.id.inputNameET)
        inputAgeET = findViewById(R.id.inputAgeET)

        usersViewLV = findViewById(R.id.usersViewLV)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, usersList)
        usersViewLV.adapter = adapter
        usersViewLV.onItemLongClickListener = AdapterView.OnItemLongClickListener { parent, v, position, id ->
            val user = adapter.getItem(position)
            adapter.remove(user)
        }

        saveBTN = findViewById(R.id.saveBTN)
        saveBTN.setOnClickListener {
            if (inputNameET.text.isEmpty() || inputAgeET.text.isEmpty()) {
                Snackbar.make(View, resources.getString(R.string.error_null), Snackbar.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            if (inputAgeET.text.toString()
                    .toInt() == null) {
                Snackbar.make(
                    View,
                    resources.getString(R.string.error_not_digit),
                    Snackbar.LENGTH_SHORT
                )
                    .show()
                return@setOnClickListener
            }
            val age = inputAgeET.text.toString().toInt()
            if (age < 1 || age > 100) {
                Snackbar.make(
                    View,
                    resources.getString(R.string.error_of_age),
                    Snackbar.LENGTH_SHORT
                )
                    .show()
                return@setOnClickListener
            }
            usersList.add(User(inputNameET.text.toString(),inputAgeET.text.toString().toInt()))
            adapter.notifyDataSetChanged()
            inputNameET.text.clear()
            inputAgeET.text.clear()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}

class User(val name: String, val age: Int)