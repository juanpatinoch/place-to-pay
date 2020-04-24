package com.placetopay.commerce.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.placetopay.commerce.BR
import com.placetopay.commerce.R
import com.placetopay.commerce.model.Products
import com.placetopay.commerce.viewmodel.PayProductViewModel

class PayProduct : AppCompatActivity() {

    private var product: Products? = null
    private var payProductViewModel: PayProductViewModel? = null
    private var activityPayProductBinding: com.placetopay.commerce.databinding.ActivityPayProductBinding? =
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
        payProductViewModel?.productPrice?.value = product?.priceText

        activityPayProductBinding?.model = payProductViewModel

        setupCurrentUserBinding()
        setupCloseActivityBinding()
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
}
