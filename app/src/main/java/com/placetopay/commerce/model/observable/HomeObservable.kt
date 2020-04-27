package com.placetopay.commerce.model.observable

import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.placetopay.commerce.model.dto.Products
import com.placetopay.commerce.model.repository.HomeRepository

class HomeObservable : BaseObservable() {

    private var repository = HomeRepository()

    //Repository
    fun callProducts() {
        repository.callProducts()
    }

    fun callCurrentUser() {
        repository.callCurrentUser()
    }

    fun callSignOut() {
        repository.callSignOut()
    }

    //ViewModel
    fun getProducts(): MutableLiveData<List<Products>> {
        return repository.getProducts()
    }

    fun getCurrentUser(): MutableLiveData<FirebaseUser> {
        return repository.getCurrentUser()
    }

    fun getSignOut(): MutableLiveData<Boolean> {
        return repository.getSignOut()
    }

    fun getLoading(): MutableLiveData<Boolean> {
        return repository.getLoading()
    }

    fun getMessageDialog(): MutableLiveData<Int> {
        return repository.getMessageDialog()
    }
}