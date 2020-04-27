package com.placetopay.commerce.model.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth

class SplashRepository {

    private var firebaseAuth: FirebaseAuth? = null
    private var goHome = MutableLiveData<Boolean>()

    fun getGoHome(): MutableLiveData<Boolean> {
        return goHome
    }

    fun callLoginStoredUser() {
        if (firebaseAuth == null)
            firebaseAuth = FirebaseAuth.getInstance()

        val user = firebaseAuth?.currentUser
        goHome.value = user?.isAnonymous
    }
}