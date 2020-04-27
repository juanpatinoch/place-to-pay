package com.placetopay.commerce.model.observable

import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.placetopay.commerce.model.repository.SplashRepository

class SplashObservable : BaseObservable() {

    private var productsRepository = SplashRepository()

    //Repository
    fun callLoginStoredUser() {
        productsRepository.callLoginStoredUser()
    }

    //ViewModel
    fun getGoHome(): MutableLiveData<Boolean> {
        return productsRepository.getGoHome()
    }
}