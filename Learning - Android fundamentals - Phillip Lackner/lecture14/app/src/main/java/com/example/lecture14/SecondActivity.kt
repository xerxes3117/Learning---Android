package com.example.lecture14

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lecture14.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBck.setOnClickListener {
            finish()
        }
    }
}