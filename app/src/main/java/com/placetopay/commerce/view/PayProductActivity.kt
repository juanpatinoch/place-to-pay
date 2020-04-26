package com.placetopay.commerce.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.placetopay.commerce.BR
import com.placetopay.commerce.R
import com.placetopay.commerce.model.Products
import com.placetopay.commerce.viewmodel.DialogMessageViewModel
import com.placetopay.commerce.viewmodel.PayProductViewModel

class PayProductActivity : AppCompatActivity() {


    private var alertDialogMessage: AlertDialog? = null
    private var alertDialogLoading: AlertDialog? = null
    private var product: Products? = null

    private var payProductViewModel: PayProductViewModel? = null
    private var activityPayProductBinding: com.placetopay.commerce.databinding.ActivityPayProductBinding? =
        null

    private var dialogMessageViewModel: DialogMessageViewModel? = null
    private var dialogMessageDataBinding: com.placetopay.commerce.databinding.DialogMessageBinding? =
        null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_product)

        product = intent.getSerializableExtra("product") as Products

        setupBinding()
    }

    private fun setupBinding() {
        activityPayProductBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_pay_product)
        payProductViewModel = ViewModelProviders.of(this).get(PayProductViewModel::class.java)

        payProductViewModel?.productName?.value = product?.name
        payProductViewModel?.productPriceValue?.value = product?.price
        payProductViewModel?.productPrice?.value = product?.priceText

        payProductViewModel?.creditCardNumber?.value = "111111111111111"
        payProductViewModel?.creditCardExpirationDate?.value = "12/24"
        payProductViewModel?.creditCardCVV?.value = "119"

        activityPayProductBinding?.model = payProductViewModel

        setupValidationBinding()
        setupCurrentUserBinding()
        setupCloseActivityBinding()
        setupTransactionResultBinding()
    }

    private fun setupTransactionResultBinding() {
        payProductViewModel?.getLoading()?.observe(this, Observer {
            if (it)
                showDialogLoading()
            else
                hideDialogLoading()
        })
        payProductViewModel?.getMessage()?.observe(this, Observer {
            //showDialog(getString(it))
        })
        payProductViewModel?.getTransaction()?.observe(this, Observer {
            val intent = Intent(this, PaymentDetailActivity::class.java)
            intent.putExtra("transaction", it)
            startActivity(intent)
            finish()
        })
    }

    var contador: Int? = null

    private fun setupValidationBinding() {
        payProductViewModel?.showValidationMessage?.observe(this, Observer {
            //showDialog(getString(R.string.pay_product_message_validateion))
        })
    }

    private fun setupCurrentUserBinding() {

        payProductViewModel?.callCurrentUser()
        payProductViewModel?.getCurrentUser()?.observe(this, Observer {
            payProductViewModel?.payerName?.value = it.displayName
            payProductViewModel?.payerEmail?.value = it.email
            payProductViewModel?.payerCellphone?.value = it.phoneNumber

            activityPayProductBinding?.setVariable(BR.model, payProductViewModel)
            activityPayProductBinding?.executePendingBindings()
        })
    }

    private fun setupCloseActivityBinding() {
        payProductViewModel?.closeActivity?.observe(this, Observer {
            if (it)
                finish()
        })
    }

    /*private fun showDialog(messageText: String) {
        *//*dialogMessageViewModel = ViewModelProviders.of(this).get(DialogMessageViewModel::class.java)
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
        })*//*
    }*/

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
