package com.placetopay.commerce.view

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.placetopay.commerce.R
import com.placetopay.commerce.model.Products
import com.placetopay.commerce.util.CreditCardDateFormattingTextWatcher
import com.placetopay.commerce.util.CreditCardNumberFormattingTextWatcher

class PayProduct : AppCompatActivity() {

    private var product: Products? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_product)

        product = intent.getSerializableExtra("product") as Products

        findViewById<TextView>(R.id.textViewPayProductName).text = product?.name
        findViewById<TextView>(R.id.textViewPayProductPrice).text = product?.priceText

        findViewById<EditText>(R.id.editTextPayProductCreditCardNumber).addTextChangedListener(
            CreditCardNumberFormattingTextWatcher()
        )
        findViewById<EditText>(R.id.editTextPayProductCreditCardExpirationDate).addTextChangedListener(
            CreditCardDateFormattingTextWatcher()
        )
    }
}
