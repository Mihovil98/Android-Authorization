package com.example.colorguess.ui.adapter

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.colorguess.R
import com.example.colorguess.databinding.ItemPersonalScoreBinding
import com.example.colorguess.di.RepositoryFactory
import com.example.colorguess.model.Score
import com.example.colorguess.ui.MainActivity

class PersonalScoreAdapter : RecyclerView.Adapter<PersonalScoreViewHolder>() {

    private val scores = mutableListOf<Score>()
    var onScoreSelectedListener: OnScoreEventListener? = null

    fun setScores(scores: List<Score>) {
        this.scores.clear()
        this.scores.addAll(scores)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalScoreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_personal_score, parent, false)
        return PersonalScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonalScoreViewHolder, position: Int) {
        val score = scores[position]
        holder.bind(score)
        onScoreSelectedListener?.let { listener ->
            val binding = ItemPersonalScoreBinding.bind(holder.itemView)
            binding.deleteButton.setOnClickListener { listener.onScoreDeleteSelected(score) }
        }
    }

    override fun getItemCount(): Int = scores.count()
}

class PersonalScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(score: Score) {
        val binding = ItemPersonalScoreBinding.bind(itemView)
        binding.itemScoreValue.text = "Score: " + score.value.toString()
        binding.itemScoreDate.text = score.date
    }
}