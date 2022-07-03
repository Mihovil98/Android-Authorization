package com.example.colorguess.ui.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.colorguess.R
import com.example.colorguess.databinding.FragmentEditBinding
import com.example.colorguess.di.RepositoryFactory
import com.example.colorguess.model.User


@Suppress("DEPRECATION")
class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding
    private val userRepository = RepositoryFactory.userRepository
    private val scoreRepository = RepositoryFactory.scoreRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val safeArgs: EditFragmentArgs by navArgs()
        val id = safeArgs.id

        var user = userRepository.getUserById(id)

        val stringUri = user!!.stringUri
        val currentUri = Uri.parse(stringUri)

        var source = activity?.let { ImageDecoder.createSource(it.contentResolver, currentUri) }
        var mYourBitmap = source?.let { ImageDecoder.decodeBitmap(it) }
        var resized = mYourBitmap?.let { Bitmap.createScaledBitmap(it, 225, 225, true) }

        binding.imageView.setImageBitmap(resized)

        binding.saveButton.setOnClickListener {
            if(binding.editTextUsername.text.toString() == ""){
                Toast.makeText(activity, "Please enter a username",
                    Toast.LENGTH_SHORT).show()
            }
            else{
                user = userRepository.getUserById(id)
                val currentUsername = user!!.username

                val scores = scoreRepository.getByUsername(currentUsername)

                val newUsername = binding.editTextUsername.text.toString()

                if(userRepository.checkUsername(newUsername) == null){

                    for (i in scores.indices) {
                        scores[i].username = newUsername
                        scoreRepository.insert(scores[i])
                    }

                    val editedUser = User(user!!.id, newUsername, user!!.email, user!!.password, user!!.stringUri)
                    userRepository.insert(editedUser)
                    binding.editTextUsername.setText("")
                    Toast.makeText(activity, "Username successfully updated",
                        Toast.LENGTH_SHORT).show()
                }
                else{
                    binding.editTextUsername.setText("")
                    Toast.makeText(activity, "Username already in use",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.selectButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.setType("image/*")
            startActivityForResult(intent,3)
        }

        binding.removeButton.setOnClickListener {
            val editedUser = User(user!!.id, user!!.username, user!!.email, user!!.password, "android.resource://com.example.colorguess/" + R.drawable.default_image)
            userRepository.insert(editedUser)

            val defaultUri = Uri.parse("android.resource://com.example.colorguess/" + R.drawable.default_image)

            source = activity?.let { ImageDecoder.createSource(it.contentResolver, defaultUri) }
            mYourBitmap = source?.let { ImageDecoder.decodeBitmap(it) }
            resized = mYourBitmap?.let { Bitmap.createScaledBitmap(it, 225, 225, true) }
            binding.imageView.setImageBitmap(resized)

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK){
            val imageUri: Uri = data?.data!!
            context!!.contentResolver.takePersistableUriPermission(imageUri, FLAG_GRANT_READ_URI_PERMISSION)

            val safeArgs: UserFragmentArgs by navArgs()
            val id = safeArgs.id

            val user = userRepository.getUserById(id)
            val editedUser = User(user!!.id, user.username, user.email, user.password, imageUri.toString())
            userRepository.insert(editedUser)

            val source = activity?.let { ImageDecoder.createSource(it.contentResolver, imageUri) }
            val mYourBitmap = source?.let { ImageDecoder.decodeBitmap(it) }
            val resized = mYourBitmap?.let { Bitmap.createScaledBitmap(it, 225, 225, true) }
            binding.imageView.setImageBitmap(resized)

        }
    }

}