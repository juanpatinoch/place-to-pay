package com.placetopay.commerce.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.placetopay.commerce.BR
import com.placetopay.commerce.R
import com.placetopay.commerce.databinding.ActivityHomeBinding
import com.placetopay.commerce.databinding.DialogMessageBinding
import com.placetopay.commerce.viewmodel.DialogMessageViewModel
import com.placetopay.commerce.viewmodel.HomeViewModel


class HomeActivity : AppCompatActivity() {

    private var alertDialogMessage: AlertDialog? = null
    private var alertDialogLoading: AlertDialog? = null

    private var viewModel: HomeViewModel? = null
    private var binding: ActivityHomeBinding? = null

    private var dialogViewModel: DialogMessageViewModel? = null
    private var dialogBinding: DialogMessageBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupBindings()
    }

    private fun setupBindings() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        binding?.model = viewModel

        openMenuBinding()
        openPaymentListBinding()
        signOutBinding()
        currentUserBinding()
        productsBinding()
        loadingBinding()
        messageBinding()
    }

    private fun openMenuBinding() {
        viewModel?.getMenuOpen()?.observe(this, Observer {
            findViewById<DrawerLayout>(R.id.drawer_layout).openDrawer(Gravity.LEFT)
        })
    }

    private fun openPaymentListBinding() {
        viewModel?.getMenuOpenPaymentList()?.observe(this, Observer {
            startActivity(Intent(this, PaymentListActivity::class.java))
        })
    }

    private fun signOutBinding() {
        viewModel?.getSignOut()?.observe(this, Observer {
            val intent = Intent(this, SplashActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        })
    }

    private fun productsBinding() {
        viewModel?.callProducts()
        viewModel?.getProducts()?.observe(this, Observer {
            viewModel?.setProductsInRecyclerAdapter(it)
        })
        viewModel?.getProductSelected()?.observe(this, Observer {
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra("product", it)
            startActivity(intent)
        })
    }

    private fun currentUserBinding() {
        viewModel?.callCurrentUser()
        viewModel?.getCurrentUser()?.observe(this, Observer {
            viewModel?.setDisplayName(it.displayName)

            binding?.setVariable(BR.model, viewModel)
            binding?.executePendingBindings()
        })
    }

    private fun messageBinding() {
        viewModel?.getMessage()?.observe(this, Observer {
            showDialog(getString(it))
        })
    }

    private fun loadingBinding() {
        viewModel?.getLoading()?.observe(this, Observer {
            if (it)
                showDialogLoading()
            else
                hideDialogLoading()
        })
    }

    private fun showDialog(messageText: String) {
        dialogViewModel = ViewModelProviders.of(this).get(DialogMessageViewModel::class.java)
        dialogViewModel?.setMessage(messageText)

        dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_message, null, false)
        dialogBinding?.model = dialogViewModel

        val builder = AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert)
        builder.setView(dialogBinding?.root)
        builder.setCancelable(false)
        alertDialogMessage = builder.show()

        dialogViewModel?.getClose()?.observe(this, Observer {
            alertDialogMessage?.dismiss()
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
}
