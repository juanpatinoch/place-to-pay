package com.placetopay.commerce.viewmodel

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.placetopay.commerce.R
import com.placetopay.commerce.model.Products
import com.placetopay.commerce.model.observable.HomeObservable
import com.placetopay.commerce.view.adapter.RecyclerProductsAdapter
import com.squareup.picasso.Picasso

class HomeViewModel : ViewModel() {

    private var recyclerProductsAdapter: RecyclerProductsAdapter? = null

    var homeObservable = HomeObservable()
    var selected: MutableLiveData<Products> = MutableLiveData()
    var openMenu = MutableLiveData<Boolean>()
    var displayName = MutableLiveData<String>()


    fun onClickOpenMenu() {
        openMenu.value = true
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
        val products: List<Products>? = homeObservable.getProducts().value
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

    fun getLoading(): MutableLiveData<Boolean> {
        return homeObservable.getLoading()
    }

    fun getMessageDialog(): MutableLiveData<Int> {
        return homeObservable.getMessageDialog()
    }

}

@BindingAdapter("imageUrl")
fun getImageProductAt(imageView: ImageView, imageUrl: String) {
    Picasso.get().load(imageUrl).resize(520, 520).centerInside().into(imageView)
}