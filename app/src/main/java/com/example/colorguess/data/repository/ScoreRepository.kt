package com.example.colorguess.data.repository

import com.example.colorguess.model.Score
import com.example.colorguess.model.User

interface ScoreRepository {
    fun insert(score: Score)
    fun delete(score: Score)
    fun deleteScores()
    fun getAll() : List<Score>
    fun getByUsername(username: String) : List<Score>
}