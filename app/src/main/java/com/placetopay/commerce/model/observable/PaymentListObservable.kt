package com.placetopay.commerce.model.observable

import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.placetopay.commerce.model.Transactions
import com.placetopay.commerce.model.repository.PaymentListRepository

class PaymentListObservable : BaseObservable() {

    private var repository = PaymentListRepository()

    //Repository
    fun callTransactions() {
        repository.callTransactions()
    }

    fun deleteTransaction(id: String) {
        repository.deleteTransaction(id)
    }

    //ViewModel
    fun getTransactions(): MutableLiveData<List<Transactions>> {
        return repository.getTransactions()
    }

    fun getLoading(): MutableLiveData<Boolean> {
        return repository.getLoading()
    }

    fun getMessage(): MutableLiveData<Int> {
        return repository.getMessage()
    }
}