package com.placetopay.commerce.viewmodel

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.placetopay.commerce.model.Transactions
import com.placetopay.commerce.model.observable.PaymentDetailObservable


class PaymentDetailViewModel : ViewModel() {

    private var paymentDetailObservable = PaymentDetailObservable()
    private var transaction = MutableLiveData<Transactions>()
    private var closeActivity = MutableLiveData<Boolean>()

    fun setTransaction(transaction: Transactions?) {
        this.transaction.value = transaction
    }

    fun getTransaction(): MutableLiveData<Transactions> {
        return transaction
    }

    fun getCloseActivity(): MutableLiveData<Boolean> {
        return closeActivity
    }

    fun setCloseActivity() {
        closeActivity.value = true
    }

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

@BindingAdapter("android:visibility")
fun setVisibility(view: View, value: Boolean) {
    view.visibility = if (value) View.VISIBLE else View.GONE
}