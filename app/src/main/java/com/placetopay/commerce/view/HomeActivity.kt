package com.placetopay.commerce.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.placetopay.commerce.R
import com.placetopay.commerce.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {

    private var homeViewModel: HomeViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        

        setupBindings(savedInstanceState)
    }

    private fun setupBindings(savedInstanceState: Bundle?) {
        var activityMainBinding: com.placetopay.commerce.databinding.ActivityHomeBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_home)

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        activityMainBinding.model = homeViewModel
        setupListUpdate()
    }

    private fun setupListUpdate() {
        homeViewModel?.callProducts()
        homeViewModel?.getProducts()?.observe(this, Observer {
            homeViewModel?.setProductsInRecyclerAdapter(it)
        })
    }
}
