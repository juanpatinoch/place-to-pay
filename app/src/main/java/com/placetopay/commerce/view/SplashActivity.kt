package com.placetopay.commerce.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.firebase.ui.auth.AuthUI
import com.placetopay.commerce.R
import com.placetopay.commerce.viewmodel.SplashViewModel

class SplashActivity : AppCompatActivity() {

    private var splashViewModel: SplashViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setupBindings(savedInstanceState)
    }

    private fun setupBindings(savedInstanceState: Bundle?) {
        var activityMainBinding: com.placetopay.commerce.databinding.ActivitySplashBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_splash)

        splashViewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        activityMainBinding.lifecycleOwner = this
        activityMainBinding.model = splashViewModel

        setupObservabelEvents()
    }

    private fun setupObservabelEvents() {
        splashViewModel?.callLoginStoredUser()
        splashViewModel?.getGoHome()?.observe(this, Observer {
            if (it != null && !it) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                login()
            }
        })
    }

    private fun login() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.mipmap.ic_launcher)
                .setTheme(R.style.FirebaseUITheme)
                .build(),
            101
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                finish()
            }
        }
    }
}
