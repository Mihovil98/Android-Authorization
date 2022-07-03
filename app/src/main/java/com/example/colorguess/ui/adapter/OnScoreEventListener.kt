package com.example.colorguess.ui.adapter

import com.example.colorguess.model.Score

interface OnScoreEventListener{
    fun onScoreDeleteSelected(score: Score?) : Boolean
}