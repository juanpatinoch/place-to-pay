package com.placetopay.commerce.model.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.placetopay.commerce.R
import com.placetopay.commerce.model.Products
import com.placetopay.commerce.util.Commons


class HomeRepository {

    private var firebaseAuth: FirebaseAuth? = null
    private var firebaseFirestore: FirebaseFirestore? = null

    private var firebaseUser = MutableLiveData<FirebaseUser>()
    private var products = MutableLiveData<List<Products>>()
    private var signOut = MutableLiveData<Boolean>()
    private var loading = MutableLiveData<Boolean>()
    private var message = MutableLiveData<Int>()

    fun getLoading(): MutableLiveData<Boolean> {
        return loading
    }

    fun getMessageDialog(): MutableLiveData<Int> {
        return message
    }

    fun getProducts(): MutableLiveData<List<Products>> {
        return products
    }

    fun callProducts() {
        try {
            loading.value = true

            if (firebaseFirestore == null)
                firebaseFirestore = FirebaseFirestore.getInstance()

            firebaseFirestore
                ?.collection("products")
                ?.addSnapshotListener { result, e ->
                    if (e != null) {
                        loading.value = false
                        message.value = R.string.home_message_error
                    } else if (result != null && !result.isEmpty) {
                        val productsList = ArrayList<Products>()

                        for (document in result) {
                            try {
                                val product = Products()
                                product.code = document.id
                                product.name = if (document.data["name"] == null) null else document.data["name"].toString()
                                product.description = if (document.data["description"] == null) null else document.data["description"].toString()
                                product.price = if (document.data["price"] == null) null else document.data["price"].toString().toInt()
                                product.image = if (document.data["image"] == null) null else document.data["image"].toString()
                                product.header = if (document.data["header"] == null) null else document.data["header"].toString()
                                product.discount = if (document.data["discount"] == null) null else document.data["discount"].toString()
                                product.priceText = if (document.data["priceText"] == null) null else Commons.getCurrencyFormat(document.data["priceText"].toString().toLong())

                                productsList.add(product)
                            } catch (e: Exception) {
                                Log.e("Error", e.message)
                            }
                        }
                        products.value = productsList
                        loading.value = false
                    } else {
                        loading.value = false
                        message.value = R.string.home_message_error
                    }
                }
        } catch (e: Exception) {
            loading.value = false
            message.value = R.string.home_message_error
        }
    }

    fun getCurrentUser(): MutableLiveData<FirebaseUser> {
        return firebaseUser
    }

    fun callCurrentUser() {
        if (firebaseAuth == null)
            firebaseAuth = FirebaseAuth.getInstance()

        firebaseUser.value = firebaseAuth?.currentUser
    }

    fun getSignOut(): MutableLiveData<Boolean> {
        return signOut
    }

    fun callSignOut() {
        if (firebaseAuth == null)
            firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth?.signOut()
        signOut.value = true
    }
}
