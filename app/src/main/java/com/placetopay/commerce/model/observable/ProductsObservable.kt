package com.placetopay.commerce.model.observable

import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.placetopay.commerce.model.Products
import com.placetopay.commerce.model.repository.ProductsRepository

class ProductsObservable : BaseObservable() {

    private var productsRepository = ProductsRepository()

    //Repository
    fun callProducts() {
        productsRepository.callProducts()
    }

    //ViewModel
    fun getProducts(): MutableLiveData<List<Products>> {
        return productsRepository.getProducts()
    }
}