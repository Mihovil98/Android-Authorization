package com.example.colorguess.data

import androidx.room.*
import com.example.colorguess.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM users")
    fun deleteUsers()

    @Query("SELECT * FROM users WHERE id =:id")
    fun getUserById(id: Long) : User?

    @Query("SELECT * FROM users WHERE username =:username")
    fun getUserByUsername(username: String) : User?

    @Query("SELECT * FROM users WHERE username =:username")
    fun checkUsername(username: String) : User?

    @Query("SELECT * FROM users WHERE email =:email")
    fun checkEmail(email: String) : User?

}