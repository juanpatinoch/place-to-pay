package com.placetopay.commerce.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DialogMessageViewModel : ViewModel() {

    private var message = MutableLiveData<String>()
    private var close = MutableLiveData<Boolean>()

    fun getMessage(): MutableLiveData<String> {
        return message
    }

    fun setMessage(message: String) {
        this.message.value = message
    }

    fun getClose(): MutableLiveData<Boolean> {
        return close
    }

    fun setClose() {
        close.value = true
    }
}