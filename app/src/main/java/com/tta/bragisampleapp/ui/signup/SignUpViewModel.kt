package com.tta.bragisampleapp.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Sign Up"
    }
    val text: LiveData<String> = _text
}