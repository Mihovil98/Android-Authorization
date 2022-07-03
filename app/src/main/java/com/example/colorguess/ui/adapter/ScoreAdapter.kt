package com.example.colorguess.ui.adapter

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.colorguess.R
import com.example.colorguess.databinding.ItemScoreBinding
import com.example.colorguess.di.RepositoryFactory
import com.example.colorguess.model.Score
import com.example.colorguess.ui.MainActivity

private val userRepository = RepositoryFactory.userRepository

class ScoreAdapter : RecyclerView.Adapter<ScoreViewHolder>() {

    private val scores = mutableListOf<Score>()

    fun setScores(scores: List<Score>) {
        this.scores.clear()
        this.scores.addAll(scores)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_score, parent, false)
        return ScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val score = scores[position]
        holder.bind(score)
    }

    override fun getItemCount(): Int = scores.count()
}

class ScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(score: Score) {
        val binding = ItemScoreBinding.bind(itemView)

        val user = userRepository.getUserByUsername(score.username)
        val stringUri = user!!.stringUri
        val currentUri = Uri.parse(stringUri)
        val source = MainActivity.applicationContext().let { ImageDecoder.createSource(it.contentResolver, currentUri) }
        val mYourBitmap = source.let { ImageDecoder.decodeBitmap(it) }
        val resized = mYourBitmap.let { Bitmap.createScaledBitmap(it, 225, 225, true) }

        binding.itemScoreImage.setImageBitmap(resized)
        binding.itemScoreUsername.text = score.username
        binding.itemScoreValue.text = "Score: " + score.value.toString()
        binding.itemScoreDate.text = score.date
    }
}