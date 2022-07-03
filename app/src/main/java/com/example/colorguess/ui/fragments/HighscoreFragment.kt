package com.example.colorguess.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.colorguess.databinding.*
import com.example.colorguess.di.RepositoryFactory
import com.example.colorguess.model.Score
import com.example.colorguess.ui.adapter.OnScoreEventListener
import com.example.colorguess.ui.adapter.ScoreAdapter

class HighscoreFragment : Fragment() {

    private lateinit var binding: FragmentHighscoreBinding
    private lateinit var adapter: ScoreAdapter
    private val scoreRepository = RepositoryFactory.scoreRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHighscoreBinding.inflate(layoutInflater)
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.scoreList.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        adapter = ScoreAdapter()
        binding.scoreList.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        updateData()
    }

    private fun updateData() {
        adapter.setScores(scoreRepository.getAll())
    }

    companion object {
        val Tag = "ScoresList"

        fun create(): Fragment {
            return HighscoreFragment()
        }
    }

}