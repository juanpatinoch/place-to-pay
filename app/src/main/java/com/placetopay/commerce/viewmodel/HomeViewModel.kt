package com.placetopay.commerce.viewmodel

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.placetopay.commerce.R
import com.placetopay.commerce.model.Products
import com.placetopay.commerce.model.observable.HomeObservable
import com.placetopay.commerce.util.SingleLiveEvent
import com.placetopay.commerce.view.adapter.RecyclerProductsAdapter
import com.squareup.picasso.Picasso

class HomeViewModel : ViewModel() {

    private var homeObservable = HomeObservable()
    private var recyclerProductsAdapter: RecyclerProductsAdapter? = null
    private val openMenu = SingleLiveEvent<Any>()
    private var displayName: String? = null

    var selected: MutableLiveData<Products> = MutableLiveData()

    val getOpenMenu: LiveData<Any>
        get() = openMenu


    fun onClickOpenMenu() {
        openMenu.call()
    }

    fun callProducts() {
        homeObservable.callProducts()
    }

    fun getProducts(): MutableLiveData<List<Products>> {
        return homeObservable.getProducts()
    }

    fun setProductsInRecyclerAdapter(products: List<Products>) {
        if (recyclerProductsAdapter == null)
            recyclerProductsAdapter =
                RecyclerProductsAdapter(
                    this,
                    R.layout.card_product
                )
        recyclerProductsAdapter?.setProductsList(products)
        recyclerProductsAdapter?.notifyDataSetChanged()
    }

    fun getRecyclerProductsAdapter(): RecyclerProductsAdapter? {
        if (recyclerProductsAdapter == null)
            recyclerProductsAdapter =
                RecyclerProductsAdapter(
                    this,
                    R.layout.card_product
                )
        return recyclerProductsAdapter
    }

    fun getProductAt(position: Int): Products? {
        var products: List<Products>? = homeObservable.getProducts().value
        return products?.get(position)
    }

    fun getProductSelected(): MutableLiveData<Products> {
        return selected
    }

    fun onProductClick(index: Int) {
        val coupon = getProductAt(index)
        selected.value = coupon
    }

    fun callCurrentUser() {
        homeObservable.callCurrentUser()
    }

    fun getCurrentUser(): MutableLiveData<FirebaseUser>? {
        return homeObservable.getCurrentUser()
    }

    fun callSignOut() {
        homeObservable.callSignOut()
    }

    fun getSignOut(): MutableLiveData<Boolean> {
        return homeObservable.getSignOut()
    }

}

@BindingAdapter("imageUrl")
fun getImageProductAt(imageView: ImageView, imageUrl: String) {
    Picasso.get().load(imageUrl).resize(520, 520).centerInside().into(imageView)
}