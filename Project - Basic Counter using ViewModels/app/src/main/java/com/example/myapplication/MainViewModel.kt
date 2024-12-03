package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private var count = MutableLiveData<Int>(0)
    val _liveCount: LiveData<Int> = count

    fun increment(){
        count.value = (count.value ?: 0) + 1
    }
}