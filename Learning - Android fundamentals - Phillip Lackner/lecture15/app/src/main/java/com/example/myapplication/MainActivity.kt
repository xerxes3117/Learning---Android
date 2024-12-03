package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            btnApply.setOnClickListener {
                val name = etName.text.toString()
                val age = etAge.text.toString().toInt()
                val country = etCountry.text.toString()
                Intent(this@MainActivity, SecondActivity::class.java).apply {
                    putExtra("EXTRA_NAME", name)
                    putExtra("EXTRA_AGE", age)
                    putExtra("EXTRA_COUNTRY", country)
                    startActivity(this)
                }
            }
        }

    }
}

