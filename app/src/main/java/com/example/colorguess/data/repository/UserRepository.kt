package com.example.colorguess.data.repository

import com.example.colorguess.model.User

interface UserRepository {

    fun insert(user: User)
    fun delete(user: User)
    fun deleteUsers()
    fun getUserById(id: Long) : User?
    fun getUserByUsername(username: String) : User?
    fun checkUsername(username: String) : User?
    fun checkEmail(email: String) : User?
}