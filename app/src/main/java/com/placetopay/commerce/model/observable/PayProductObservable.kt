package com.placetopay.commerce.model.observable

import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.placetopay.commerce.model.repository.PayProductRepository

class PayProductObservable : BaseObservable() {

    private var payProductRepository = PayProductRepository()

    //Repository
    fun callCurrentUser() {
        payProductRepository.callCurrentUser()
    }

    //ViewModel
    fun getCurrentUser(): MutableLiveData<FirebaseUser> {
        return payProductRepository.getCurrentUser()
    }

}