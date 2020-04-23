package com.placetopay.commerce.view

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
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
        activityMainBinding.lifecycleOwner = this
        activityMainBinding.model = homeViewModel

        setupObservabelEvents()
    }

    private fun setupObservabelEvents() {
        homeViewModel?.callProducts()

        homeViewModel?.getOpenMenu?.observe(this, Observer {
            findViewById<DrawerLayout>(R.id.drawer_layout).openDrawer(Gravity.LEFT)
        })

        homeViewModel?.getProducts()?.observe(this, Observer {
            homeViewModel?.setProductsInRecyclerAdapter(it)
        })

        homeViewModel?.getProductSelected()?.observe(this, Observer {
            val intent = Intent(this, ProductDetailActivity::class.java).apply {
                putExtra("product", it)
            }
            startActivity(intent)
        })
    }
}
