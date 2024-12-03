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

        val sharedPref = getSharedPreferences("myPref", MODE_PRIVATE)
        val editor = sharedPref.edit()

        with(binding) {
            button1.setOnClickListener {
                val name = etName.text
                val age = etAge.text
                val isAdult = checkBox.isChecked

                editor.apply {
                    putString("name", name.toString())
                    putString("age", age.toString())
                    putBoolean("isAdult", isAdult)
                    apply()
                }
            }

            button2.setOnClickListener {
                val name = sharedPref.getString("name", "")
                val age = sharedPref.getString("age", "")
                val isAdult = sharedPref.getBoolean("isAdult", false)

                etName.setText(name)
                etAge.setText(age)
                checkBox.isChecked = isAdult}
        }
    }
}