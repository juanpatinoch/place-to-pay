package com.placetopay.commerce.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DialogMessageViewModel : ViewModel() {

    var messageText = MutableLiveData<String>()
    var closeDialog = MutableLiveData<Boolean>()

    fun onClickCloseDialog() {
        if (closeDialog.value == null)
            closeDialog.value = true
        else
            closeDialog.value = !closeDialog.value!!
    }

}