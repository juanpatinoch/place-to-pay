package com.placetopay.commerce.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.placetopay.commerce.R
import com.placetopay.commerce.model.Transactions
import com.placetopay.commerce.model.observable.PaymentListObservable
import com.placetopay.commerce.view.adapter.RecyclerPaymentsAdapter

class PaymentListViewModel : ViewModel() {

    private var selected: MutableLiveData<Transactions> = MutableLiveData()
    private var recyclerPaymentsAdapter: RecyclerPaymentsAdapter? = null
    private var paymentListObservable = PaymentListObservable()

    fun getTransactionAtPosition(position: Int): Transactions? {
        val products: List<Transactions>? = paymentListObservable.getTransactions().value
        return products?.get(position)
    }

    fun callTransactions() {
        paymentListObservable.callTransactions()
    }

    fun getTransactions(): MutableLiveData<List<Transactions>> {
        return paymentListObservable.getTransactions()
    }

    fun setPaymentsInRecyclerAdapter(transactions: List<Transactions>) {
        if (recyclerPaymentsAdapter == null)
            recyclerPaymentsAdapter =
                RecyclerPaymentsAdapter(
                    this,
                    R.layout.card_payment
                )
        recyclerPaymentsAdapter?.setTransactionsList(transactions)
        recyclerPaymentsAdapter?.notifyDataSetChanged()
    }

    fun getRecyclerTransactionsAdapter(): RecyclerPaymentsAdapter? {
        if (recyclerPaymentsAdapter == null)
            recyclerPaymentsAdapter =
                RecyclerPaymentsAdapter(
                    this,
                    R.layout.card_payment
                )
        return recyclerPaymentsAdapter
    }

    fun onTransactionClick(index: Int) {
        selected.value = getTransactionAtPosition(index)
    }

    fun getTransactionSelected(): MutableLiveData<Transactions> {
        return selected
    }

    fun deleteTransaction(id: String){
        paymentListObservable.deleteTransaction(id)
    }

}