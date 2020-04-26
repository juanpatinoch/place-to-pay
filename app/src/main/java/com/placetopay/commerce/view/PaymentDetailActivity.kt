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
import com.placetopay.commerce.model.Transactions
import com.placetopay.commerce.viewmodel.DialogMessageViewModel
import com.placetopay.commerce.viewmodel.PaymentDetailViewModel

class PaymentDetailActivity : AppCompatActivity() {

    private var alertDialogMessage: AlertDialog? = null
    private var alertDialogLoading: AlertDialog? = null
    private var transaction: Transactions? = null

    private var paymentDetailViewModel: PaymentDetailViewModel? = null
    private var activityPaymentDetailBinding: com.placetopay.commerce.databinding.ActivityPaymentDetailBinding? =
        null

    private var dialogMessageViewModel: DialogMessageViewModel? = null
    private var dialogMessageDataBinding: com.placetopay.commerce.databinding.DialogMessageBinding? =
        null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_detail)

        transaction = intent.getSerializableExtra("transaction") as Transactions
        setupBinding()
        setupRefreshStatusBinding()
        setupLoadingBinding()
        setupMessageDialogBinding()
        setupCloseActivityBinding()
    }

    private fun setupBinding() {
        activityPaymentDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_payment_detail)
        paymentDetailViewModel = ViewModelProviders.of(this).get(PaymentDetailViewModel::class.java)

        paymentDetailViewModel?.setTransaction(transaction)

        activityPaymentDetailBinding?.model = paymentDetailViewModel
    }

    private fun setupRefreshStatusBinding() {
        paymentDetailViewModel?.getRefreshStatus()?.observe(this, Observer {
            paymentDetailViewModel?.setTransaction(it)
            activityPaymentDetailBinding?.setVariable(BR.model, paymentDetailViewModel)
            activityPaymentDetailBinding?.executePendingBindings()
        })
    }

    private fun setupMessageDialogBinding() {
        paymentDetailViewModel?.getMessageDialog()?.observe(this, Observer {
            showDialog(getString(it))
        })
    }

    private fun setupLoadingBinding() {
        paymentDetailViewModel?.getLoading()?.observe(this, Observer {
            if (it)
                showDialogLoading()
            else
                hideDialogLoading()
        })
    }

    private fun setupCloseActivityBinding() {
        paymentDetailViewModel?.getCloseActivity()?.observe(this, Observer {
            finish()
        })
    }

    private fun showDialog(messageText: String) {
        /*dialogMessageViewModel = ViewModelProviders.of(this).get(DialogMessageViewModel::class.java)
        dialogMessageViewModel?.messageText?.value = messageText

        dialogMessageDataBinding =
            DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_message, null, false)
        dialogMessageDataBinding?.model = dialogMessageViewModel

        val builder = AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert)
        builder.setView(dialogMessageDataBinding?.root)
        builder.setCancelable(false)
        alertDialogMessage = builder.show()

        dialogMessageViewModel?.closeDialog?.observe(this, Observer {
            if (it) {
                alertDialogMessage?.dismiss()
                dialogMessageViewModel?.closeDialog?.value = false
            }
        })*/
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
