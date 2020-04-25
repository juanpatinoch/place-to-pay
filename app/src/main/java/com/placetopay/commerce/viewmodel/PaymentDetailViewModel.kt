package com.placetopay.commerce.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.placetopay.commerce.model.Transactions
import com.placetopay.commerce.model.observable.PaymentDetailObservable

class PaymentDetailViewModel : ViewModel() {

    var paymentDetailObservable = PaymentDetailObservable()
    var transaction = MutableLiveData<Transactions>()

    fun onRefreshClick() {
        paymentDetailObservable.callRefreshStatus(transaction.value!!)
    }

    fun getMessageDialog(): MutableLiveData<Int> {
        return paymentDetailObservable.getMessage()
    }

    fun getLoading(): MutableLiveData<Boolean> {
        return paymentDetailObservable.getLoading()
    }

    fun getRefreshStatus(): MutableLiveData<Transactions> {
        return paymentDetailObservable.getRefreshStatus()
    }

}