package com.placetopay.commerce.model.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.placetopay.commerce.model.Products
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class HomeRepository {

    private val tag = "ProductsRepository"

    private var firebaseAuth: FirebaseAuth? = null
    private var firebaseFirestore: FirebaseFirestore? = null

    private var products = MutableLiveData<List<Products>>()
    private var firebaseUser = MutableLiveData<FirebaseUser>()

    private var signOut = MutableLiveData<Boolean>()

    fun getProducts(): MutableLiveData<List<Products>> {
        return products
    }

    fun callProducts() {
        try {
            if (firebaseFirestore == null)
                firebaseFirestore = FirebaseFirestore.getInstance()

            firebaseFirestore?.collection("products")?.addSnapshotListener { result, e ->

                if (e != null) {
                    Log.e(tag, "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (result != null && !result.isEmpty) {
                    var productsList = ArrayList<Products>()

                    val format: NumberFormat = NumberFormat.getCurrencyInstance()
                    format.maximumFractionDigits = 0
                    format.currency = Currency.getInstance("COP")

                    for (document in result) {
                        var product = Products()
                        product.code = document.id
                        product.name = document.data["name"].toString()
                        product.description = document.data["description"].toString()
                        product.price = document.data["price"].toString().toInt()
                        product.image = document.data["image"].toString()
                        product.header = document.data["header"].toString()
                        product.discount = document.data["discount"].toString()
                        product.priceText =
                            format.format(document.data["price"].toString().toLong())

                        productsList.add(product)
                    }
                    products.value = productsList
                } else {
                    Log.e(tag, "Current data: null")
                }
            }
        } catch (e: Exception) {
            Log.e(tag, e.message)
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
