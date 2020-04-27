package com.placetopay.commerce.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.placetopay.commerce.R
import com.placetopay.commerce.databinding.ActivityPaymentListBinding
import com.placetopay.commerce.databinding.DialogMessageBinding
import com.placetopay.commerce.viewmodel.DialogMessageViewModel
import com.placetopay.commerce.viewmodel.PaymentListViewModel

class PaymentListActivity : AppCompatActivity() {

    private var alertDialogMessage: AlertDialog? = null
    private var alertDialogLoading: AlertDialog? = null

    private var viewModel: PaymentListViewModel? = null
    private var binding: ActivityPaymentListBinding? = null

    private var dialogViewModel: DialogMessageViewModel? = null
    private var dialogBinding: DialogMessageBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_list)

        setupBindings()
    }

    private fun setupBindings() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_list)
        viewModel = ViewModelProviders.of(this).get(PaymentListViewModel::class.java)
        binding?.model = viewModel

        transactionsBinding()
        loadingBinding()
        messageBinding()
        closeBinding()
    }

    private fun transactionsBinding() {
        viewModel?.callTransactions()
        viewModel?.getTransactions()?.observe(this, Observer {
            viewModel?.setPaymentsInRecyclerAdapter(it)
        })
        viewModel?.getTransactionSelected()?.observe(this, Observer {
            val intent = Intent(this, PaymentDetailActivity::class.java)
            intent.putExtra("transaction", it)
            startActivity(intent)
        })
    }

    private fun closeBinding() {
        viewModel?.getClose()?.observe(this, Observer {
            finish()
        })
    }

    private fun messageBinding() {
        viewModel?.getMessage()?.observe(this, Observer {
            showDialog(getString(it))
        })
    }

    private fun loadingBinding() {
        viewModel?.getLoading()?.observe(this, Observer {
            if (it)
                showDialogLoading()
            else
                hideDialogLoading()
        })
    }

    private fun showDialog(messageText: String) {
        dialogViewModel = ViewModelProviders.of(this).get(DialogMessageViewModel::class.java)
        dialogViewModel?.setMessage(messageText)

        dialogBinding =
            DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_message, null, false)
        dialogBinding?.model = dialogViewModel

        val builder = AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert)
        builder.setView(dialogBinding?.root)
        builder.setCancelable(false)
        alertDialogMessage = builder.show()

        dialogViewModel?.getClose()?.observe(this@PaymentListActivity, Observer {
            if (it) {
                alertDialogMessage?.dismiss()
                dialogViewModel?.getClose()?.removeObservers(this@PaymentListActivity)
                dialogViewModel?.setClose(false)
            }
        })
    }

    private fun showDialogLoading() {
        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.dialog_loading, null)
        val builder = AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert)
        builder.setView(view)
        builder.setCancelable(false)
        alertDialogLoading = builder.show()
    }

    private fun hideDialogLoading() {
        if (alertDialogLoading != null)
            alertDialogLoading?.dismiss()
    }
}
