package com.example.listview

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.serializableparcelable.testOfData

class MainActivity : AppCompatActivity() {
    private lateinit var toolbarMain: Toolbar
    private lateinit var inputNameET: EditText
    private lateinit var inputAgeET: EditText
    private lateinit var saveBTN: Button
    private lateinit var usersViewLV: ListView
    lateinit var userViewModel: UserViewModel

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

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        val adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, userViewModel.usersList)

        userViewModel.actualList.observe(this, Observer{
            usersViewLV.adapter = adapter
        })

        usersViewLV = findViewById(R.id.usersViewLV)
        usersViewLV.onItemClickListener = DeleteAlert.makeDialog(this, adapter, userViewModel)

        saveBTN = findViewById(R.id.saveBTN)
        saveBTN.setOnClickListener { View ->
            val imm =
                View.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(View.windowToken, 0)
            val name = inputNameET.text.toString()
            if (testOfData(
                    this,
                    name,
                    inputAgeET.text.toString()
                ) == false
            ) {
                return@setOnClickListener
            }
            val age = inputAgeET.text.toString().toInt()
            userViewModel.addUser(name, age)
            userViewModel.actualList.value = userViewModel.usersList
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

class User(val name: String, val age: Int) {
    override fun toString() = "$name, возраст - $age."
}