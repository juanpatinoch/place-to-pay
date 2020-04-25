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
import com.placetopay.commerce.viewmodel.DialogMessageViewModel
import com.placetopay.commerce.viewmodel.HomeViewModel


class HomeActivity : AppCompatActivity() {

    private var alertDialogMessage: AlertDialog? = null
    private var alertDialogLoading: AlertDialog? = null

    private var homeViewModel: HomeViewModel? = null
    private var activityHomeDataBinding: com.placetopay.commerce.databinding.ActivityHomeBinding? =
        null

    private var dialogMessageViewModel: DialogMessageViewModel? = null
    private var dialogMessageDataBinding: com.placetopay.commerce.databinding.DialogMessageBinding? =
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
        setupLoadingBinding()
        setupMessageDialogBinding()
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

    private fun setupMessageDialogBinding() {
        homeViewModel?.getMessageDialog()?.observe(this, Observer {
            showDialog(getString(it))
        })
    }

    private fun setupLoadingBinding() {
        homeViewModel?.getLoading()?.observe(this, Observer {
            if (it)
                showDialogLoading()
            else
                hideDialogLoading()
        })
    }

    private fun showDialog(messageText: String) {
        dialogMessageViewModel = ViewModelProviders.of(this).get(DialogMessageViewModel::class.java)
        dialogMessageViewModel?.messageText?.value = messageText

        dialogMessageDataBinding =
            DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_message, null, false)
        dialogMessageDataBinding?.model = dialogMessageViewModel

        val builder = AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert)
        builder.setView(dialogMessageDataBinding?.root)
        builder.setCancelable(false)
        alertDialogMessage = builder.show()

        dialogMessageViewModel?.closeDialog?.observe(this, Observer {
            if (it) {
                alertDialogMessage?.dismiss()
                dialogMessageViewModel?.closeDialog?.value = false
            }
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
