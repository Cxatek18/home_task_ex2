package com.team.gamecircle.presentation

import android.os.Bundle
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.team.gamecircle.R
import com.team.gamecircle.data.network.ApiFactory
import com.team.gamecircle.data.network.model.ImageInfo
import com.team.gamecircle.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var startRotation: Float = 0f
    private var restScrolling: Float = 0f
    private var degreeDisplacement: Float = 0f
    private var imageInfo: ImageInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestingImage()
        clickButtonSpinDrum()
        clickButtonSpinAgain()
    }

    private fun clickButtonSpinDrum() {
        binding.buttonSpinDrum.setOnClickListener {
            var randomRotationEnd: Int = Random.nextInt(
                RANDOM_ROTATION_END_START,
                RANDOM_ROTATION_END_END
            ) * RANDOM_ROTATION_END_RATION
            var randomRotationBy: Float = Random.nextInt(
                RANDOM_ROTATION_START,
                randomRotationEnd
            ).toFloat()
            restScrolling = randomRotationBy % 360f
            degreeDisplacement = 360 - restScrolling
            binding.circleViewCustom.animate()
                .rotation(startRotation)
                .rotationBy(randomRotationBy)
                .withStartAction {
                    setStartSettingAnimation()
                }
                .setDuration(TIME_ANIMATION)
                .withEndAction {
                    setEndSettingsAnimation(degreeDisplacement)
                    binding.circleViewCustom.rotation = 0f
                }
        }
    }

    private fun clickButtonSpinAgain() {
        binding.buttonSpinAgain.setOnClickListener {
            binding.buttonSpinAgain.visibility = INVISIBLE
            binding.resultCustomView.visibility = INVISIBLE
            binding.textView.text = ""
            binding.buttonSpinDrum.visibility = VISIBLE
            binding.circleViewCustom.visibility = VISIBLE
            binding.imageWinningArrow.visibility = VISIBLE
            binding.buttonSpinDrum.isClickable = true
            binding.buttonSpinDrum.setBackgroundColor(
                ContextCompat.getColor(this, R.color.background_button)
            )
        }
    }

    private fun requestingImage() {
        CoroutineScope(Dispatchers.IO).launch {
            val responseImageInfo = ApiFactory.apiService.getImage()
            runOnUiThread {
                imageInfo = responseImageInfo
            }
        }
    }

    private fun setStartSettingAnimation() {
        binding.buttonSpinDrum.isClickable = false
        binding.buttonSpinDrum.setBackgroundColor(
            ContextCompat.getColor(this, R.color.background_button_block)
        )
        binding.textView.text = ""
    }

    private fun setEndSettingsAnimation(degreeDisplacement: Float) {
        binding.buttonSpinDrum.visibility = GONE
        binding.circleViewCustom.visibility = GONE
        binding.imageWinningArrow.visibility = GONE
        val isText: Boolean = gettingColor(degreeDisplacement.toInt())
        if (isText) {
            binding.resultCustomView.isText = isText
        } else {
            binding.resultCustomView.isText = isText
            binding.resultCustomView.imageInfo = imageInfo
        }
        binding.buttonSpinAgain.visibility = VISIBLE
        binding.resultCustomView.visibility = VISIBLE
    }

    private fun gettingColor(degreeDisplacement: Int): Boolean {
        when (degreeDisplacement) {
            in 17 until 67 -> {
                binding.textView.text = "Жёлтый"
                return true
            }

            in 68 until 118 -> {
                binding.textView.text = "Зелёный"
                return false
            }

            in 119 until 169 -> {
                binding.textView.text = "Голубой"
                return true
            }

            in 170 until 220 -> {
                binding.textView.text = "Синий"
                return false
            }

            in 221 until 271 -> {
                binding.textView.text = "Фиолетовый"
                return true
            }

            in 272 until 322 -> {
                binding.textView.text = "Красный"
                return true
            }

            in 323 until 360 -> {
                binding.textView.text = "Оранжевый"
                return false
            }

            in 0 until 16 -> {
                binding.textView.text = "Оранжевый"
                return false
            }

            else -> return true
        }
    }

    companion object {
        private const val TIME_ANIMATION: Long = 5000L
        private const val RANDOM_ROTATION_START = 360
        private const val RANDOM_ROTATION_END_START = 380
        private const val RANDOM_ROTATION_END_END = 450
        private const val RANDOM_ROTATION_END_RATION = 4
    }
}