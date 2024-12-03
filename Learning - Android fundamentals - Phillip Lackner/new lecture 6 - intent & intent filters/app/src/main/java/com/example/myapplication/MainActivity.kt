package com.example.myapplication

import android.content.Intent
import android.net.Uri
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

        with(binding){
            btnNew.setOnClickListener(){
                // Do something
//                val intent = Intent(Intent.ACTION_VIEW)
//                intent.data = Uri.parse("https://www.google.com")
//                startActivity(intent)

                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.data = Uri.parse("file:///sdcard/mydocument.pdf")
                intent.type = "application/pdf"
                startActivity(intent)
            }
        }
    }
}