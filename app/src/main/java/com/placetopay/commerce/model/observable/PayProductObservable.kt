package com.placetopay.commerce.model.observable

import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.placetopay.commerce.model.dto.PayProduct
import com.placetopay.commerce.model.dto.Transactions
import com.placetopay.commerce.model.repository.PayProductRepository

class PayProductObservable : BaseObservable() {

    private var repository = PayProductRepository()

    //Repository
    fun setMessage(message: Int) {
        repository.setMessage(message)
    }

    fun callCurrentUser() {
        repository.callCurrentUser()
    }

    fun payProduct(payProduct: PayProduct) {
        repository.payProduct(payProduct)
    }

    //ViewModel
    fun getCurrentUser(): MutableLiveData<FirebaseUser> {
        return repository.getCurrentUser()
    }

    fun getTransaction(): MutableLiveData<Transactions> {
        return repository.getTransaction()
    }

    fun getMessage(): MutableLiveData<Int> {
        return repository.getMessage()
    }

    fun getLoading(): MutableLiveData<Boolean> {
        return repository.getLoading()
    }

}