package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Dispatchers.Main).launch {
            doSomething()
        }

        GlobalScope.launch {
            delay(1000)
            println("Hello")
        }
        println("World")
    }

    private suspend fun doSomething() {
//        CoroutineScope(Dispatchers.IO).launch {
//            val fbFollowers = async { getFbFollowers() }
//            val instFollowers = async { getInstFollowers() }
//            Log.d("test", "FB followers: ${fbFollowers.await()} Inst followers: \${instFollowers.await()")
//        }

        val fb = CoroutineScope(Dispatchers.IO).async { getFbFollowers() }
        val inst = CoroutineScope(Dispatchers.IO).async { getInstFollowers() }

        Log.d("test", "FB followers: ${fb.await()} Inst followers: ${inst.await()}")
    }

    private suspend fun getFbFollowers(): Int {
        delay(1000)
        return 40
    }

    private suspend fun getInstFollowers(): Int {
        delay(1000)
        return 50
    }
}