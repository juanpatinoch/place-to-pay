package com.placetopay.commerce.model.observable

import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.placetopay.commerce.model.Transactions
import com.placetopay.commerce.model.repository.PaymentListRepository

class PaymentListObservable : BaseObservable() {

    private var paymentListRepository = PaymentListRepository()

    //Repository
    fun callTransactions() {
        paymentListRepository.callTransactions()
    }

    fun deleteTransaction(id: String){
        paymentListRepository.deleteTransaction(id)
    }

    //ViewModel
    fun getTransactions(): MutableLiveData<List<Transactions>> {
        return paymentListRepository.getTransactions()
    }
}