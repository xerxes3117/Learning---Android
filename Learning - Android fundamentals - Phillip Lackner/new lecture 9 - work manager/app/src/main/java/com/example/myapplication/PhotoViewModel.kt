package com.example.myapplication

import androidx.lifecycle.ViewModel

class PhotoViewModel: ViewModel() {
    var photoUri: String? = null
    var photoPath: String? = null
    var compressionThreshold: Long = 0
}