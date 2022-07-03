package com.example.colorguess.ui.fragments

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.colorguess.R
import com.example.colorguess.databinding.*
import com.example.colorguess.di.RepositoryFactory
import com.example.colorguess.model.Score
import com.example.colorguess.ui.adapter.OnScoreEventListener
import com.example.colorguess.ui.adapter.PersonalScoreAdapter
import com.example.colorguess.ui.adapter.ScoreAdapter
import java.time.LocalDate

class ProfileFragment : Fragment(), OnScoreEventListener {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var adapter: PersonalScoreAdapter
    private val userRepository = RepositoryFactory.userRepository
    private val scoreRepository = RepositoryFactory.scoreRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        setupRecyclerView()

        val safeArgs: UserFragmentArgs by navArgs()
        val id = safeArgs.id
        val user = userRepository.getUserById(id)
        val stringUri = user!!.stringUri
        val currentUri = Uri.parse(stringUri)
        val source = activity?.let { ImageDecoder.createSource(it.contentResolver, currentUri) }
        val mYourBitmap = source?.let { ImageDecoder.decodeBitmap(it) }
        val resized = mYourBitmap?.let { Bitmap.createScaledBitmap(it, 225, 225, true) }
        binding.profileImage.setImageBitmap(resized)

        binding.username.text = user.username

        binding.editButton.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToEditFragment(id)
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.personalScores.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        adapter = PersonalScoreAdapter()
        adapter.onScoreSelectedListener = this
        binding.personalScores.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        updateData()
    }

    private fun updateData() {
        val safeArgs: UserFragmentArgs by navArgs()
        val id = safeArgs.id
        val user = userRepository.getUserById(id)
        adapter.setScores(scoreRepository.getByUsername(user!!.username))
    }

    companion object {
        val Tag = "PersonalScoresList"

        fun create(): Fragment {
            return ProfileFragment()
        }
    }

    override fun onScoreDeleteSelected(score: Score?): Boolean {
        score?.let { it ->
            context?.let { it1 ->
                AlertDialog.Builder(it1)
                    .setTitle("Delete score?")
                    .setMessage("This action is permanent and cannot be reversed")
                    .setIcon(R.mipmap.ic_launcher)
                    .setPositiveButton("YES"){ dialog, which ->
                        scoreRepository.delete(it)
                        updateData()
                    }
                    .setNegativeButton("NO") { dialog, which ->
                    }
                    .create()
                    .show()
            }
        }
        return true
    }

}