package com.placetopay.commerce.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.placetopay.commerce.BR
import com.placetopay.commerce.R
import com.placetopay.commerce.databinding.ActivityPayProductBinding
import com.placetopay.commerce.databinding.DialogMessageBinding
import com.placetopay.commerce.model.Products
import com.placetopay.commerce.viewmodel.DialogMessageViewModel
import com.placetopay.commerce.viewmodel.PayProductViewModel

class PayProductActivity : AppCompatActivity() {


    private var alertDialogMessage: AlertDialog? = null
    private var alertDialogLoading: AlertDialog? = null

    private var viewModel: PayProductViewModel? = null
    private var binding: ActivityPayProductBinding? = null

    private var dialogViewModel: DialogMessageViewModel? = null
    private var dialogBinding: DialogMessageBinding? = null

    private var product: Products? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_product)

        product = intent.getSerializableExtra("product") as Products

        setupBinding()
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pay_product)
        viewModel = ViewModelProviders.of(this).get(PayProductViewModel::class.java)

        viewModel?.productName?.value = product?.name
        viewModel?.productPriceValue?.value = product?.price
        viewModel?.productPrice?.value = product?.priceText

        binding?.model = viewModel

        loadingBinding()
        messageBinding()
        closeActivityBinding()
        currentUserBinding()
        transactionBinding()
    }

    private fun loadingBinding() {
        viewModel?.getLoading()?.observe(this, Observer {
            if (it)
                showDialogLoading()
            else
                hideDialogLoading()
        })
    }

    private fun messageBinding() {
        viewModel?.getMessage()?.observe(this, Observer {
            showDialog(getString(it))
        })
    }

    private fun transactionBinding() {
        viewModel?.getTransaction()?.observe(this, Observer {
            val intent = Intent(this, PaymentDetailActivity::class.java)
            intent.putExtra("transaction", it)
            startActivity(intent)
            finish()
        })
    }

    private fun currentUserBinding() {
        viewModel?.callCurrentUser()
        viewModel?.getCurrentUser()?.observe(this, Observer {
            viewModel?.payerName?.value = it.displayName
            viewModel?.payerEmail?.value = it.email
            viewModel?.payerCellphone?.value = it.phoneNumber

            binding?.setVariable(BR.model, viewModel)
            binding?.executePendingBindings()
        })
    }

    private fun closeActivityBinding() {
        viewModel?.getClose()?.observe(this, Observer {
            finish()
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

        dialogViewModel?.getClose()?.observe(this@PayProductActivity, Observer {
            if (it) {
                alertDialogMessage?.dismiss()
                dialogViewModel?.getClose()?.removeObservers(this@PayProductActivity)
                dialogViewModel?.setClose(false)
            }
        })
    }
}
