package com.placetopay.commerce.viewmodel

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.placetopay.commerce.model.Products
import com.squareup.picasso.Picasso

class ProductDetailViewModel : ViewModel() {

    private var product = MutableLiveData<Products>()
    private var buy = MutableLiveData<Boolean>()
    private var close = MutableLiveData<Boolean>()

    fun getClose(): MutableLiveData<Boolean> {
        return close
    }

    fun setClose() {
        close.value = true
    }

    fun getBuy(): MutableLiveData<Boolean> {
        return buy
    }

    fun setBuy() {
        buy.value = true
    }

    fun getProduct(): MutableLiveData<Products> {
        return product
    }

    fun setProduct(product: Products?) {
        this.product.value = product
    }

}