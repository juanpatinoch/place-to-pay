package com.placetopay.commerce.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.placetopay.commerce.R
import com.placetopay.commerce.viewmodel.PaymentListViewModel

class PaymentListActivity : AppCompatActivity() {

    private var paymentListViewModel: PaymentListViewModel? = null
    private var activityPaymentListBinding: com.placetopay.commerce.databinding.ActivityPaymentListBinding? =
        null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_list)

        setupBindings(savedInstanceState)
    }

    private fun setupBindings(savedInstanceState: Bundle?) {
        activityPaymentListBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_payment_list)
        paymentListViewModel = ViewModelProviders.of(this).get(PaymentListViewModel::class.java)
        activityPaymentListBinding?.model = paymentListViewModel

        setupTransactionsBinding()
    }

    private fun setupTransactionsBinding() {
        paymentListViewModel?.callTransactions()
        paymentListViewModel?.getTransactions()?.observe(this, Observer {
            paymentListViewModel?.setPaymentsInRecyclerAdapter(it)
        })
        paymentListViewModel?.getTransactionSelected()?.observe(this, Observer {
            val intent = Intent(this, PaymentDetailActivity::class.java)
            intent.putExtra("transaction", it)
            startActivity(intent)
        })
    }
}
