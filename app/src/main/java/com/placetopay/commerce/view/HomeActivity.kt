package com.placetopay.commerce.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.placetopay.commerce.R
import com.placetopay.commerce.viewmodel.ProductsViewModel

class HomeActivity : AppCompatActivity() {

    private var productsViewModel: ProductsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupBindings(savedInstanceState)
    }

    fun setupBindings(savedInstanceState: Bundle?) {
        var activityMainBinding: com.placetopay.commerce.databinding.ActivityHomeBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_home)

        productsViewModel = ViewModelProviders.of(this).get(ProductsViewModel::class.java)
        activityMainBinding.model = productsViewModel
        setupListUpdate()
    }

    fun setupListUpdate() {
        productsViewModel?.callProducts()
        productsViewModel?.getProducts()?.observe(this, Observer {
            productsViewModel?.setProductsInRecyclerAdapter(it)
        })
    }
}
