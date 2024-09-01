package com.example.listview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel: ViewModel() {
    var usersList: MutableList<User> = mutableListOf()
    val actualList: MutableLiveData<MutableList<User>> by lazy {MutableLiveData<MutableList<User>>()}
    fun addUser (name: String, age: Int) {usersList.add(User(name, age))}
}