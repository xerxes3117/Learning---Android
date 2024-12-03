package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val firstFragment = FirstFragment()
        val secondFragment = SecondFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayoutFragment, firstFragment)
            commit()
        }

        with(binding) {
            btnFragment1.setOnClickListener {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.frameLayoutFragment, firstFragment)
                    addToBackStack(null)
                    commit()
                }
            }

            btnFragment2.setOnClickListener {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.frameLayoutFragment , secondFragment)
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }
}