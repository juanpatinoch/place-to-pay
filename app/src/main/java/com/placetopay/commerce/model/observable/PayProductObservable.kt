package com.placetopay.commerce.model.observable

import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.placetopay.commerce.model.Transactions
import com.placetopay.commerce.model.repository.PayProductRepository

class PayProductObservable : BaseObservable() {

    private var payProductRepository = PayProductRepository()

    //Repository
    fun callCurrentUser() {
        payProductRepository.callCurrentUser()
    }

    fun payProduct(
        productName: String,
        productPrice: Int,
        payerName: String,
        payerEmail: String,
        payerCellphone: String,
        creditCardNumber: String,
        creditCardDate: String,
        creditCardCVV: String
    ) {
        payProductRepository.payProduct(
            productName,
            productPrice,
            payerName,
            payerEmail,
            payerCellphone,
            creditCardNumber,
            creditCardDate,
            creditCardCVV
        )
    }

    //ViewModel
    fun getCurrentUser(): MutableLiveData<FirebaseUser> {
        return payProductRepository.getCurrentUser()
    }

    fun getTransaction(): MutableLiveData<Transactions> {
        return payProductRepository.getTransaction()
    }

    fun getMessage(): MutableLiveData<Int> {
        return payProductRepository.getMessage()
    }

    fun getLoading(): MutableLiveData<Boolean> {
        return payProductRepository.getLoading()
    }

}