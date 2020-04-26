package com.placetopay.commerce.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.placetopay.commerce.BR
import com.placetopay.commerce.model.Transactions
import com.placetopay.commerce.viewmodel.PaymentListViewModel

class RecyclerPaymentsAdapter(var paymentListViewModel: PaymentListViewModel, var resource: Int) :
    RecyclerView.Adapter<RecyclerPaymentsAdapter.CardPaymentHolder>() {

    private var transactions: List<Transactions>? = null

    fun setTransactionsList(transactions: List<Transactions>) {
        this.transactions = transactions
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardPaymentHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(layoutInflater, viewType, parent, false)
        return CardPaymentHolder(binding)
    }

    override fun getItemCount(): Int {
        return transactions?.size ?: 0
    }

    override fun onBindViewHolder(holder: CardPaymentHolder, position: Int) {
        holder.setDataCard(paymentListViewModel, position)
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    fun getLayoutIdForPosition(position: Int): Int {
        return resource
    }

    class CardPaymentHolder(viewDataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        private var viewDataBinding: ViewDataBinding? = null

        init {
            this.viewDataBinding = viewDataBinding
        }

        fun setDataCard(paymentListViewModel: PaymentListViewModel, position: Int) {
            viewDataBinding?.setVariable(BR.model, paymentListViewModel)
            viewDataBinding?.setVariable(BR.position, position)
            viewDataBinding?.executePendingBindings()
        }
    }

}