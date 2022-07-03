package com.example.colorguess.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.colorguess.databinding.*
import com.example.colorguess.di.RepositoryFactory

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val userRepository = RepositoryFactory.userRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirmLogin.setOnClickListener {
            val user = userRepository.checkEmail(binding.editTextEmailLogin.text.toString())
            if(user != null){
                if(user.password == binding.editTextPasswordLogin.text.toString()){
                    val action = LoginFragmentDirections.actionLoginFragmentToUserFragment(user.id)
                    findNavController().navigate(action)

                    Toast.makeText(activity, "User logged in",
                        Toast.LENGTH_SHORT).show()
                }
                else{
                    binding.editTextEmailLogin.setText("")
                    binding.editTextPasswordLogin.setText("")
                    Toast.makeText(activity, "Email and password are not matching",
                        Toast.LENGTH_SHORT).show()
                }
            }
            else{
                binding.editTextEmailLogin.setText("")
                binding.editTextPasswordLogin.setText("")
                Toast.makeText(activity, "Email and password are not matching",
                    Toast.LENGTH_SHORT).show()
            }
        }

        binding.confirmRegister.setOnClickListener {

            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)

        }

    }

}