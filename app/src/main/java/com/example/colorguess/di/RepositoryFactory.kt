package com.example.colorguess.di

import com.example.colorguess.data.repository.ScoreRepository
import com.example.colorguess.data.repository.ScoreRepositoryImpl
import com.example.colorguess.data.repository.UserRepository
import com.example.colorguess.data.repository.UserRepositoryImpl
import com.example.colorguess.data.room.UserDatabase
import com.example.colorguess.ui.MainActivity

object RepositoryFactory {
    val roomDb = UserDatabase.getDatabase(MainActivity.applicationContext())
    val userRepository: UserRepository = UserRepositoryImpl(roomDb.getUserDao())
    val scoreRepository: ScoreRepository = ScoreRepositoryImpl(roomDb.getScoreDao())
}