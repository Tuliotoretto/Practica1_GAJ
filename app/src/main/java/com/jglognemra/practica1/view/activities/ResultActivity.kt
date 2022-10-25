package com.jglognemra.practica1.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jglognemra.practica1.R
import com.jglognemra.practica1.databinding.ActivityResultBinding
import kotlin.math.round

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val result = intent.getDoubleExtra(getString(R.string.intent_result), 0.0)
        binding.tvResult.text = (round(result * 100) / 100).toString()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}