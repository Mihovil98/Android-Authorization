package com.example.colorguess.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "scores")
data class Score(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @ColumnInfo(name = "username")
    var username: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "value")
    val value: Int
)