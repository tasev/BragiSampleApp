package com.tta.bragisampleapp.ui.forgotpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ForgotPasswordViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Forgot Password"
    }
    val text: LiveData<String> = _text
}