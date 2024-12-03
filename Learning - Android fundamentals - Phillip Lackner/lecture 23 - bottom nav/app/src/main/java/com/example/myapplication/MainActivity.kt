package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentFirst = FirstFragment()
        val fragmentSecond = SecondFragment()
        val fragmentThird = ThirdFragment()

        setFragment(fragmentFirst)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> setFragment(fragmentFirst)
                R.id.message -> setFragment(fragmentSecond)
                R.id.profile -> setFragment(fragmentThird)
            }
            true
        }

        binding.bottomNavigationView.getOrCreateBadge(R.id.message).apply {
            isVisible = true
            number = 10
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}