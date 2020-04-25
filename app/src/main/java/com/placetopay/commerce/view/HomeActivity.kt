package com.placetopay.commerce.view

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.placetopay.commerce.BR
import com.placetopay.commerce.R
import com.placetopay.commerce.viewmodel.HomeViewModel


class HomeActivity : AppCompatActivity() {

    private var homeViewModel: HomeViewModel? = null
    private var activityHomeDataBinding: com.placetopay.commerce.databinding.ActivityHomeBinding? =
        null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupBindings(savedInstanceState)
    }

    private fun setupBindings(savedInstanceState: Bundle?) {
        activityHomeDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        activityHomeDataBinding?.model = homeViewModel

        setupOpenMenuClickBinding()
        setupSignOutClickBinding()
        setupCurrentUserBinding()
        setupProductsBinding()
    }

    private fun setupProductsBinding() {
        homeViewModel?.callProducts()
        homeViewModel?.getProducts()?.observe(this, Observer {
            homeViewModel?.setProductsInRecyclerAdapter(it)
        })
        homeViewModel?.getProductSelected()?.observe(this, Observer {
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra("product", it)
            startActivity(intent)
        })
    }

    private fun setupCurrentUserBinding() {
        homeViewModel?.callCurrentUser()
        homeViewModel?.getCurrentUser()?.observe(this, Observer {
            homeViewModel?.displayName?.value = it.displayName

            activityHomeDataBinding?.setVariable(BR.model, homeViewModel)
            activityHomeDataBinding?.executePendingBindings()
        })
    }

    private fun setupOpenMenuClickBinding() {
        homeViewModel?.openMenu?.observe(this, Observer {
            findViewById<DrawerLayout>(R.id.drawer_layout).openDrawer(Gravity.LEFT)
        })
    }

    private fun setupSignOutClickBinding() {
        homeViewModel?.getSignOut()?.observe(this, Observer {
            val intent = Intent(this, SplashActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        })
    }
}
