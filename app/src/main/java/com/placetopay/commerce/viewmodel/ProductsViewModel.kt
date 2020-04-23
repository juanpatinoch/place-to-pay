package com.placetopay.commerce.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.placetopay.commerce.R
import com.placetopay.commerce.model.Products
import com.placetopay.commerce.model.observable.ProductsObservable
import com.placetopay.commerce.view.RecyclerProductsAdapter

class ProductsViewModel : ViewModel() {

    private var productsObservable = ProductsObservable()
    private var recyclerProductsAdapter: RecyclerProductsAdapter? = null

    fun callProducts() {
        productsObservable.callProducts()
    }

    fun getProducts(): MutableLiveData<List<Products>> {
        return productsObservable.getProducts()
    }

    fun setProductsInRecyclerAdapter(products: List<Products>) {
        if (recyclerProductsAdapter == null)
            recyclerProductsAdapter = RecyclerProductsAdapter(this, R.layout.card_product)
        recyclerProductsAdapter?.setProductsList(products)
        recyclerProductsAdapter?.notifyDataSetChanged()
    }

    fun getRecyclerProductsAdapter(): RecyclerProductsAdapter? {
        if (recyclerProductsAdapter == null)
            recyclerProductsAdapter = RecyclerProductsAdapter(this, R.layout.card_product)
        return recyclerProductsAdapter
    }

    fun getProductAt(position: Int): Products? {
        var products: List<Products>? = productsObservable.getProducts().value
        return products?.get(position)
    }
}