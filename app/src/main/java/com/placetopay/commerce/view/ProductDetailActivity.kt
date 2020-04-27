package com.placetopay.commerce.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.placetopay.commerce.R
import com.placetopay.commerce.databinding.ActivityProductDetailBinding
import com.placetopay.commerce.model.dto.Products
import com.placetopay.commerce.viewmodel.ProductDetailViewModel

class ProductDetailActivity : AppCompatActivity() {

    private var product: Products? = null
    private var viewModel: ProductDetailViewModel? = null
    private var binding: ActivityProductDetailBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        product = intent.getSerializableExtra("product") as Products

        setupBindings()
    }

    private fun setupBindings() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        viewModel = ViewModelProviders.of(this).get(ProductDetailViewModel::class.java)
        viewModel?.setProduct(product)
        binding?.model = viewModel

        closeBinding()
        buyBinding()
    }

    private fun closeBinding() {
        viewModel?.getClose()?.observe(this, Observer {
            finish()
        })
    }

    private fun buyBinding() {
        viewModel?.getBuy()?.observe(this, Observer {
            val intent = Intent(this, PayProductActivity::class.java)
            intent.putExtra("product", product)
            startActivity(intent)
            finish()
        })
    }
}
