package com.example.colorguess.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.colorguess.R
import com.example.colorguess.databinding.*
import com.example.colorguess.di.RepositoryFactory


class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding
    private val taskRepository = RepositoryFactory.userRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val safeArgs: UserFragmentArgs by navArgs()
        val id = safeArgs.id

        val user = taskRepository.getUserById(id)
        val username = user?.username

        binding.text.text = "Welcome " + username + "!"

        binding.playButton.setOnClickListener {

            val action = UserFragmentDirections.actionUserFragmentToGameFragment(id)
            findNavController().navigate(action)

        }

        binding.profileButton.setOnClickListener {

            val action = UserFragmentDirections.actionUserFragmentToProfileFragment(id)
            findNavController().navigate(action)

        }

        binding.highscoreButton.setOnClickListener {

            val action = UserFragmentDirections.actionUserFragmentToHighscoreFragment()
            findNavController().navigate(action)

        }

        binding.logoutButton.setOnClickListener {
            val action = UserFragmentDirections.actionUserFragmentToLoginFragment()
            context?.let { it1 ->
                AlertDialog.Builder(it1)
                    .setTitle("Logout?")
                    .setIcon(R.mipmap.ic_launcher)
                    .setPositiveButton("YES"){ dialog, which ->
                        findNavController().navigate(action)
                    }
                    .setNegativeButton("NO") { dialog, which ->
                    }
                    .create()
                    .show()
            }
        }
    }
}