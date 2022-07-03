package com.example.colorguess.data.repository

import com.example.colorguess.data.UserDao
import com.example.colorguess.model.User

class UserRepositoryImpl(val userDao: UserDao) : UserRepository {
    override fun insert(user: User) = userDao.insert(user)
    override fun delete(user: User) = userDao.delete(user)
    override fun deleteUsers() = userDao.deleteUsers()
    override fun getUserById(id: Long) : User? = userDao.getUserById(id)
    override fun getUserByUsername(username: String) : User? = userDao.getUserByUsername(username)
    override fun checkUsername(username: String) : User? = userDao.checkUsername(username)
    override fun checkEmail(email: String) : User? = userDao.checkEmail(email)
}