package com.example.colorguess.data

import androidx.room.*
import com.example.colorguess.model.Score
import com.example.colorguess.model.User

@Dao
interface ScoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(score: Score)

    @Delete
    fun delete(score: Score)

    @Query("DELETE FROM scores")
    fun deleteScores()

    @Query("SELECT * FROM scores ORDER BY value DESC")
    fun getAll(): List<Score>

    @Query("SELECT * FROM scores WHERE username = :username ORDER BY value DESC")
    fun getByUsername(username: String): List<Score>

}