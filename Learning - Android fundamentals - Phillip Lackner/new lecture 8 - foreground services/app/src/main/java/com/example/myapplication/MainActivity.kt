package com.example.myapplication

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.POST_NOTIFICATIONS,
                    android.Manifest.permission.MANAGE_OWN_CALLS,
                    android.Manifest.permission.FOREGROUND_SERVICE_PHONE_CALL,
                ),
                0
            )
        }

        with(binding){
            btnStart.setOnClickListener {
                Intent(applicationContext, RunningService::class.java).also {
                    it.action = RunningService.State.STARTED.toString()
                    startService(it)
                }
            }
            btnStop.setOnClickListener {
                Intent(applicationContext, RunningService::class.java).also {
                    it.action = RunningService.State.STOPPED.toString()
                    startService(it)
                }
            }
        }
    }
}