package com.example.serializableparcelable

import android.content.Context
import android.widget.Toast
import com.example.listview.R

fun testOfData(th: Context, name: String, age: String): Boolean {
    if (name == "" || age == "") {
        Toast.makeText(th, th.resources.getString(R.string.error_null), Toast.LENGTH_SHORT).show()
        return false
    }
    if (age.toIntOrNull() == null) {
        Toast.makeText(th, th.resources.getString(R.string.error_not_digit), Toast.LENGTH_SHORT).show()
        return false
    }
    val ageInt = age.toInt()
    if (ageInt < 1 || ageInt > 100) {
        Toast.makeText(th, th.resources.getString(R.string.error_of_age), Toast.LENGTH_SHORT).show()
        return false
    }
    return true
}