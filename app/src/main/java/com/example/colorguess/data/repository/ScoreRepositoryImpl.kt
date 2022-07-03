package com.example.colorguess.data.repository

import com.example.colorguess.data.ScoreDao
import com.example.colorguess.model.Score

class ScoreRepositoryImpl(val scoreDao: ScoreDao) : ScoreRepository {
    override fun insert(score: Score) = scoreDao.insert(score)
    override fun delete(score: Score) = scoreDao.delete(score)
    override fun deleteScores() = scoreDao.deleteScores()
    override fun getAll() : List<Score> = scoreDao.getAll()
    override fun getByUsername(username: String) : List<Score> = scoreDao.getByUsername(username)
}