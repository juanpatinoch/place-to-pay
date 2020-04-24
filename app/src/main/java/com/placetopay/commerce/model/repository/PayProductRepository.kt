package com.placetopay.commerce.model.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class PayProductRepository {

    private var firebaseAuth: FirebaseAuth? = null
    private var firebaseFirestore: FirebaseFirestore? = null

    private var firebaseUser = MutableLiveData<FirebaseUser>()

    fun getCurrentUser(): MutableLiveData<FirebaseUser> {
        return firebaseUser
    }

    fun callCurrentUser() {
        if (firebaseAuth == null)
            firebaseAuth = FirebaseAuth.getInstance()

        firebaseUser.value = firebaseAuth?.currentUser
    }

}