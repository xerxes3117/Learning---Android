package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val images = listOf(
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5
        )

        with(binding) {
            viewPager.adapter = ViewPagerAdapter(images)

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = "Tab ${position + 1}"
            }.attach()

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    // Handle tab selection
                    Toast.makeText(
                        this@MainActivity,
                        "Selected tab: ${tab?.text}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    // Handle tab unselection
                    Toast.makeText(
                        this@MainActivity,
                        "Unselected tab: ${tab?.text}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    // Handle tab reselection
                    Toast.makeText(
                        this@MainActivity,
                        "Reselected tab: ${tab?.text}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }
}