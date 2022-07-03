package com.example.colorguess.ui.fragments

import android.graphics.Color
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.colorguess.R
import com.example.colorguess.databinding.*
import com.example.colorguess.di.RepositoryFactory
import com.example.colorguess.model.Score
import java.time.LocalDate
import kotlin.random.Random


@Suppress("DEPRECATION")
class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private val userRepository = RepositoryFactory.userRepository
    private val scoreRepository = RepositoryFactory.scoreRepository

    val colors = arrayOf<String>("PURPLE", "BLUE", "PINK", "ORANGE", "GREEN")
    val hexColors = arrayOf<String>("#7F58AF", "#64C5EB", "#E84D8A", "#FEB326", "#6D8B74")
    var correctAnswer = ""

    private lateinit var mSoundPool: SoundPool
    private var mLoaded: Boolean = false
    var mSoundMap: HashMap<Int, Int> = HashMap()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(layoutInflater)
        this.loadSounds()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val safeArgs: UserFragmentArgs by navArgs()
        val id = safeArgs.id
        val user = userRepository.getUserById(id)

        binding.time.setVisibility(View.INVISIBLE)
        binding.score.setVisibility(View.INVISIBLE)
        binding.color1.setVisibility(View.INVISIBLE)
        binding.color2.setVisibility(View.INVISIBLE)
        binding.color3.setVisibility(View.INVISIBLE)

        var scoreValue = 0
        binding.score.text = "SCORE: " + scoreValue.toString()

        binding.startButton.setOnClickListener{
            binding.startButton.setVisibility(View.INVISIBLE)
            binding.score.setVisibility(View.VISIBLE)
            binding.time.setVisibility(View.VISIBLE)
            binding.color1.setVisibility(View.VISIBLE)
            binding.color2.setVisibility(View.VISIBLE)
            binding.color3.setVisibility(View.VISIBLE)

            reset()

            object : CountDownTimer(30000, 1000) {

                override fun onTick(millisUntilFinished: Long) {
                    binding.time.setText("TIME LEFT: " + millisUntilFinished / 1000)
                }

                override fun onFinish() {
                    binding.time.setVisibility(View.INVISIBLE)
                    binding.startButton.setVisibility(View.VISIBLE)
                    binding.color1.setVisibility(View.INVISIBLE)
                    binding.color2.setVisibility(View.INVISIBLE)
                    binding.color3.setVisibility(View.INVISIBLE)
                    correctAnswer = ""
                    context?.let { it1 ->
                        AlertDialog.Builder(it1)
                            .setTitle("Save score?")
                            .setMessage("Score: " + scoreValue.toString())
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton("YES"){ dialog, which ->
                                val score = Score(0, user!!.username, LocalDate.now().toString(), scoreValue)
                                scoreRepository.insert(score)
                                scoreValue = 0
                                binding.score.text = "SCORE: " + scoreValue.toString()
                                binding.score.setVisibility(View.INVISIBLE)
                            }
                            .setNegativeButton("NO") { dialog, which ->
                                scoreValue = 0
                                binding.score.text = "SCORE: " + scoreValue.toString()
                                binding.score.setVisibility(View.INVISIBLE)
                            }
                            .create()
                            .show()
                    }
                }
            }.start()
        }

        binding.purpleButton.setOnClickListener{
            if(correctAnswer == "PURPLE"){
                scoreValue++
                binding.score.text = "SCORE: " + scoreValue.toString()
                playSound(R.raw.correct)
                reset()
            }else if(correctAnswer == ""){

            }else{
                scoreValue--
                binding.score.text = "SCORE: " + scoreValue.toString()
                playSound(R.raw.wrong)
                reset()
            }
        }

        binding.blueButton.setOnClickListener{
            if(correctAnswer == "BLUE"){
                scoreValue++
                binding.score.text = "SCORE: " + scoreValue.toString()
                playSound(R.raw.correct)
                reset()
            }else if(correctAnswer == ""){

            }else{
                scoreValue--
                binding.score.text = "SCORE: " + scoreValue.toString()
                playSound(R.raw.wrong)
                reset()
            }
        }

        binding.pinkButton.setOnClickListener{
            if(correctAnswer == "PINK"){
                scoreValue++
                binding.score.text = "SCORE: " + scoreValue.toString()
                playSound(R.raw.correct)
                reset()
            }else if(correctAnswer == ""){

            }else{
                scoreValue--
                binding.score.text = "SCORE: " + scoreValue.toString()
                playSound(R.raw.wrong)
                reset()
            }
        }

        binding.orangeButton.setOnClickListener{
            if(correctAnswer == "ORANGE"){
                scoreValue++
                binding.score.text = "SCORE: " + scoreValue.toString()
                playSound(R.raw.correct)
                reset()
            }else if(correctAnswer == ""){

            }else{
                scoreValue--
                binding.score.text = "SCORE: " + scoreValue.toString()
                playSound(R.raw.wrong)
                reset()
            }
        }

        binding.greenButton.setOnClickListener{
            if(correctAnswer == "GREEN"){
                scoreValue++
                binding.score.text = "SCORE: " + scoreValue.toString()
                playSound(R.raw.correct)
                reset()
            }else if(correctAnswer == ""){

            }else{
                scoreValue--
                binding.score.text = "SCORE: " + scoreValue.toString()
                playSound(R.raw.wrong)
                reset()
            }
        }
    }

    fun reset(){
        var randomPosition = Random.nextInt(6)
        var color1 = binding.color1
        var color2 = binding.color2
        var color3 = binding.color3

        if(randomPosition == 0){
            color1 = binding.color1
            color2 = binding.color2
            color3 = binding.color3
        }else if(randomPosition == 1){
            color1 = binding.color1
            color3 = binding.color2
            color2 = binding.color3
        }else if(randomPosition == 2){
            color2 = binding.color1
            color1 = binding.color2
            color3 = binding.color3
        }else if(randomPosition == 3){
            color2 = binding.color1
            color3 = binding.color2
            color1 = binding.color3
        }else if(randomPosition == 4){
            color3 = binding.color1
            color1 = binding.color2
            color2 = binding.color3
        }else if(randomPosition == 5){
            color3 = binding.color1
            color2 = binding.color2
            color1 = binding.color3
        }

        var randomColor = Random.nextInt(5)

        correctAnswer = colors[randomColor]

        color1.text = correctAnswer
        color2.setTextColor(Color.parseColor(hexColors[randomColor]))

        var randomWrong1 = 0
        var randomWrong2 = 0
        var randomWrong3 = 0
        var randomWrong4 = 0

        do{
            randomWrong1 = Random.nextInt(5)
        }while(randomWrong1 == randomColor)

        do {
            randomWrong2 = Random.nextInt(5)
        }while(randomWrong2 == randomColor || randomWrong2 == randomWrong1);

        do {
            randomWrong3 = Random.nextInt(5)
        }while(randomWrong3 == randomColor || randomWrong3 == randomWrong1 || randomWrong3 == randomWrong2)

        do {
            randomWrong4 = Random.nextInt(5)
        }while(randomWrong4 == randomColor || randomWrong4 == randomWrong1 || randomWrong4 == randomWrong2 || randomWrong4 == randomWrong3)

        color2.text = colors[randomWrong1];

        color1.setTextColor(Color.parseColor(hexColors[randomWrong2]))

        color3.text = colors[randomWrong3];
        color3.setTextColor(Color.parseColor(hexColors[randomWrong4]))

    }

    private fun loadSounds() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.mSoundPool = SoundPool.Builder().setMaxStreams(10).build()
        } else {
            this.mSoundPool = SoundPool(10, AudioManager.STREAM_MUSIC, 0)
        }
        this.mSoundPool.setOnLoadCompleteListener { _, _, _ -> mLoaded = true }
        this.mSoundMap[R.raw.correct] = this.mSoundPool.load(context, R.raw.correct, 1)
        this.mSoundMap[R.raw.wrong] = this.mSoundPool.load(context, R.raw.wrong, 1)
    }

    fun playSound(selectedSound: Int) {
        val soundID = this.mSoundMap[selectedSound] ?: 0
        this.mSoundPool.play(soundID, 1f, 1f, 1, 0, 1f)
    }

}