package com.example.rickandmorty.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.BuildConfig

class SplashViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        val versionCode = BuildConfig.VERSION_CODE
        val versionName = BuildConfig.VERSION_NAME
        value = String.format("The version is %d (%s)", versionCode, versionName)
    }
    val text: LiveData<String> = _text
}