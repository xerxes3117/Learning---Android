package com.example.permissions

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.permissions.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("Permissions: ", getAndroidVersion())

        with(binding) {
            btnRequestPermissions.setOnClickListener { requestPermissions() }
        }
    }

    private fun getAndroidVersion(): String {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> "Android 12"
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> "Android 11"
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> "Android 10"
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.P -> "Android 9"
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> "Android 8"
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> "Android 7"
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> "Android 6"
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> "Android 5"
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> "Android 4.4"
            else -> "Older version"
        }
    }

    private fun hasExternalStoragePermission() =
        ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    private fun hasLocationForegroundPermission() =
        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun hasLocationBackgroundPermission() =
        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun requestPermissions() {
        val permissions = mutableListOf<String>()
        if (!hasExternalStoragePermission()) {
            Log.d("Permissions Request", "Requesting external storage permission")
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (!hasLocationForegroundPermission()) {
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (!hasLocationBackgroundPermission() && hasLocationForegroundPermission()) {
            permissions.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
        if (permissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissions.toTypedArray(), 0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 0 && grantResults.isNotEmpty()) {
            for(i in grantResults.indices){
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    // Permission granted
                    Log.d("Permissions Request", "${permissions[i]} granted")
                }
            }
        }
    }
}
