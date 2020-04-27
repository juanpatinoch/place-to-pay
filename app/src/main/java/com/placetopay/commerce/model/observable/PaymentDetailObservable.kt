package com.placetopay.commerce.model.observable

import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.placetopay.commerce.model.dto.Transactions
import com.placetopay.commerce.model.repository.PaymentDetailRepository

class PaymentDetailObservable : BaseObservable() {

    private var paymenDetailRepository = PaymentDetailRepository()

    //Repository
    fun callRefreshStatus(transaction: Transactions) {
        return paymenDetailRepository.callRefreshStatus(transaction)
    }


    //ViewModel
    fun getMessage(): MutableLiveData<Int> {
        return paymenDetailRepository.getMessage()
    }

    fun getLoading(): MutableLiveData<Boolean> {
        return paymenDetailRepository.getLoading()
    }

    fun getRefreshStatus(): MutableLiveData<Transactions> {
        return paymenDetailRepository.getRefreshStatus()
    }
}