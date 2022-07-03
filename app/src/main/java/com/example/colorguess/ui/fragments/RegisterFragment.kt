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
import com.example.colorguess.model.User

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val userRepository = RepositoryFactory.userRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirmRegister.setOnClickListener {

            if(binding.editTextUsernameRegister.text.toString() == "" ||
                binding.editTextEmailRegister.text.toString() == "" ||
                    binding.editTextPasswordRegister.text.toString() == "" ||
                    binding.editTextPasswordConfirmRegister.text.toString() == ""){
                Toast.makeText(activity, "Please fill out all fields",
                    Toast.LENGTH_SHORT).show()
            }

            else{
                if(userRepository.checkUsername(binding.editTextUsernameRegister.text.toString()) == null){
                    if(userRepository.checkEmail(binding.editTextEmailRegister.text.toString()) == null){
                        if(binding.editTextPasswordRegister.text.toString() == binding.editTextPasswordConfirmRegister.text.toString()){
                            val username = binding.editTextUsernameRegister.text.toString()
                            val email = binding.editTextEmailRegister.text.toString()
                            val password = binding.editTextPasswordRegister.text.toString()
                            val user = User(0, username, email, password)

                            userRepository.insert(user)

                            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                            findNavController().navigate(action)

                            Toast.makeText(activity, "User registered",
                                Toast.LENGTH_SHORT).show()
                        }
                        else{
                            binding.editTextPasswordRegister.setText("")
                            binding.editTextPasswordConfirmRegister.setText("")
                            Toast.makeText(activity, "Passwords are not matching",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        binding.editTextEmailRegister.setText("")
                        Toast.makeText(activity, "Email already in use",
                            Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    binding.editTextUsernameRegister.setText("")
                    Toast.makeText(activity, "Username already in use",
                        Toast.LENGTH_SHORT).show()
                }
            }

        }

    }
}