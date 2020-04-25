package com.placetopay.commerce.view

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.placetopay.commerce.R
import com.placetopay.commerce.model.Products
import com.squareup.picasso.Picasso

class ProductDetailActivity : AppCompatActivity() {

    private var product: Products? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        product = intent.getSerializableExtra("product") as Products

        setProductData()
        setEvents()
    }

    private fun setProductData() {
        findViewById<TextView>(R.id.textViewProductDetailName).text = product?.name
        findViewById<TextView>(R.id.textViewProductDetailPrice).text = product?.priceText
        findViewById<TextView>(R.id.textViewProductDetailDiscount).text = product?.discount
        findViewById<TextView>(R.id.textViewProductDetailDescription).text = product?.description

        Picasso.get().load(product?.image).resize(520, 520).centerInside()
            .into(findViewById<ImageView>(R.id.imageViewProductDetail))
    }

    private fun setEvents() {
        findViewById<MaterialButton>(R.id.buttonProductDetailBuy).setOnClickListener {
            val intent = Intent(this, PayProductActivity::class.java)
            intent.putExtra("product", product)
            startActivity(intent)
            finish()
        }
        findViewById<ImageView>(R.id.imageViewProductDetailBack).setOnClickListener {
            finish()
        }
    }
}
