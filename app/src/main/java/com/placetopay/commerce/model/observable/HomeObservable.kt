package com.placetopay.commerce.model.observable

import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.placetopay.commerce.model.Products
import com.placetopay.commerce.model.repository.HomeRepository

class HomeObservable : BaseObservable() {

    private var homeRepository = HomeRepository()

    //Repository
    fun callProducts() {
        homeRepository.callProducts()
    }

    fun callCurrentUser() {
        homeRepository.callCurrentUser()
    }

    fun callSignOut() {
        homeRepository.callSignOut()
    }

    //ViewModel
    fun getProducts(): MutableLiveData<List<Products>> {
        return homeRepository.getProducts()
    }

    fun getCurrentUser(): MutableLiveData<FirebaseUser> {
        return homeRepository.getCurrentUser()
    }

    fun getSignOut(): MutableLiveData<Boolean> {
        return homeRepository.getSignOut()
    }
}