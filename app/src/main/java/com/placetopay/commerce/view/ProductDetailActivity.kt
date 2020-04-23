package com.placetopay.commerce.view

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.placetopay.commerce.R
import com.placetopay.commerce.model.Products
import com.squareup.picasso.Picasso

class ProductDetailActivity : AppCompatActivity() {

    private var product: Products? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        product = intent.getSerializableExtra("product") as Products

        val name = findViewById<TextView>(R.id.textViewProductDetailName)
        val price = findViewById<TextView>(R.id.textViewProductDetailPrice)
        val discount = findViewById<TextView>(R.id.textViewProductDetailDiscount)
        val description = findViewById<TextView>(R.id.textViewProductDetailDescription)
        val image = findViewById<ImageView>(R.id.imageViewProductDetail)

        name.text = product?.name
        price.text = product?.priceText
        discount.text = product?.discount
        description.text = product?.description

        Picasso.get().load(product?.image).resize(520, 520).centerInside().into(image)
    }
}
