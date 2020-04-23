package com.placetopay.commerce.model.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.placetopay.commerce.model.Products

class ProductsRepository {

    private val tag = "ProductsRepository"

    private var firebaseFirestore: FirebaseFirestore? = null
    private var products = MutableLiveData<List<Products>>()

    fun getProducts(): MutableLiveData<List<Products>> {
        return products
    }

    fun callProducts() {
        try {
            if (firebaseFirestore == null)
                firebaseFirestore = FirebaseFirestore.getInstance()

            firebaseFirestore?.collection("products")?.get()
                ?.addOnSuccessListener { result ->
                    var productsList = ArrayList<Products>()
                    for (document in result) {
                        var product = Products()
                        product.code = document.id
                        product.name = document.data["name"].toString()
                        product.description = document.data["description"].toString()
                        product.price = document.data["price"].toString().toInt()
                        product.image = document.data["image"].toString()
                        product.header = document.data["header"].toString()
                        product.discount = document.data["discount"].toString()
                        product.priceText = document.data["price"].toString()

                        productsList.add(product)
                    }
                    products.value = productsList
                }
                ?.addOnFailureListener {
                    Log.e(tag, it.message)
                }
        } catch (e: Exception) {
            Log.e(tag, e.message)
        }

    }

}