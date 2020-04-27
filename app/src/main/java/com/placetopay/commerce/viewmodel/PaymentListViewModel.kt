package com.placetopay.commerce.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.placetopay.commerce.R
import com.placetopay.commerce.model.Transactions
import com.placetopay.commerce.model.observable.PaymentListObservable
import com.placetopay.commerce.view.adapter.RecyclerPaymentsAdapter

class PaymentListViewModel : ViewModel() {

    private var observable = PaymentListObservable()
    private var recyclerPaymentsAdapter: RecyclerPaymentsAdapter? = null
    private var selected: MutableLiveData<Transactions> = MutableLiveData()
    private var close = MutableLiveData<Boolean>()

    fun getClose(): MutableLiveData<Boolean> {
        return close
    }

    fun setClose() {
        close.value = true
    }

    fun getLoading(): MutableLiveData<Boolean> {
        return observable.getLoading()
    }

    fun getMessage(): MutableLiveData<Int> {
        return observable.getMessage()
    }

    fun callTransactions() {
        observable.callTransactions()
    }

    fun getTransactions(): MutableLiveData<List<Transactions>> {
        return observable.getTransactions()
    }

    fun getTransactionAtPosition(position: Int): Transactions? {
        val products: List<Transactions>? = observable.getTransactions().value
        return products?.get(position)
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

    fun setTransactionSelected(index: Int) {
        selected.value = getTransactionAtPosition(index)
    }

    fun getTransactionSelected(): MutableLiveData<Transactions> {
        return selected
    }

    fun deleteTransaction(id: String) {
        observable.deleteTransaction(id)
    }

}