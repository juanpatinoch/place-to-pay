package com.placetopay.commerce.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.placetopay.commerce.model.observable.SplashObservable

class SplashViewModel : ViewModel() {

    private var splashObservable = SplashObservable()

    fun callLoginStoredUser() {
        splashObservable.callLoginStoredUser()
    }

    fun getGoHome(): MutableLiveData<Boolean> {
        return splashObservable.getGoHome()
    }

}