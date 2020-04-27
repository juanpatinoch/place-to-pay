package com.placetopay.commerce.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.placetopay.commerce.BR
import com.placetopay.commerce.R
import com.placetopay.commerce.databinding.ActivityPaymentDetailBinding
import com.placetopay.commerce.databinding.DialogMessageBinding
import com.placetopay.commerce.model.dto.Transactions
import com.placetopay.commerce.viewmodel.DialogMessageViewModel
import com.placetopay.commerce.viewmodel.PaymentDetailViewModel

class PaymentDetailActivity : AppCompatActivity() {

    private var alertDialogMessage: AlertDialog? = null
    private var alertDialogLoading: AlertDialog? = null

    private var transaction: Transactions? = null

    private var viewModel: PaymentDetailViewModel? = null
    private var binding: ActivityPaymentDetailBinding? = null

    private var dialogViewModel: DialogMessageViewModel? = null
    private var dialogBinding: DialogMessageBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_detail)

        transaction = intent.getSerializableExtra("transaction") as Transactions
        setupBinding()
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_detail)
        viewModel = ViewModelProviders.of(this).get(PaymentDetailViewModel::class.java)

        viewModel?.setTransaction(transaction)

        binding?.model = viewModel

        refreshStatusBinding()
        messageBinding()
        loadingBinding()
        closeBinding()
    }

    private fun refreshStatusBinding() {
        viewModel?.getRefreshStatus()?.observe(this, Observer {
            viewModel?.setTransaction(it)
            binding?.setVariable(BR.model, viewModel)
            binding?.executePendingBindings()
        })
    }

    private fun messageBinding() {
        viewModel?.getMessageDialog()?.observe(this, Observer {
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

    private fun closeBinding() {
        viewModel?.getClose()?.observe(this, Observer {
            finish()
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

        dialogViewModel?.getClose()?.observe(this@PaymentDetailActivity, Observer {
            if (it) {
                alertDialogMessage?.dismiss()
                dialogViewModel?.getClose()?.removeObservers(this@PaymentDetailActivity)
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
