package com.placetopay.commerce.viewmodel

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.placetopay.commerce.R
import com.placetopay.commerce.model.dto.Products
import com.placetopay.commerce.model.observable.HomeObservable
import com.placetopay.commerce.view.adapter.RecyclerProductsAdapter
import com.squareup.picasso.Picasso

class HomeViewModel : ViewModel() {

    private var observable = HomeObservable()
    private var recyclerProductsAdapter: RecyclerProductsAdapter? = null

    private var selected: MutableLiveData<Products> = MutableLiveData()
    private var displayName = MutableLiveData<String>()
    private var menuOpen = MutableLiveData<Boolean>()
    private var menuOpenPaymentList = MutableLiveData<Boolean>()

    fun getDisplayName(): MutableLiveData<String> {
        return displayName
    }

    fun setDisplayName(displayName: String?) {
        this.displayName.value = displayName
    }

    fun getMenuOpen(): MutableLiveData<Boolean> {
        return menuOpen
    }

    fun setMenuOpen() {
        menuOpen.value = true
    }

    fun getMenuOpenPaymentList(): MutableLiveData<Boolean> {
        return menuOpenPaymentList
    }

    fun setMenuOpenPaymentList() {
        menuOpenPaymentList.value = true
    }

    fun getProducts(): MutableLiveData<List<Products>> {
        return observable.getProducts()
    }

    fun callProducts() {
        observable.callProducts()
    }

    fun getRecyclerProductsAdapter(): RecyclerProductsAdapter? {
        if (recyclerProductsAdapter == null)
            recyclerProductsAdapter = RecyclerProductsAdapter(this, R.layout.card_product)

        return recyclerProductsAdapter
    }

    fun setProductsInRecyclerAdapter(products: List<Products>) {
        if (recyclerProductsAdapter == null)
            recyclerProductsAdapter = RecyclerProductsAdapter(this, R.layout.card_product)

        recyclerProductsAdapter?.setProductsList(products)
        recyclerProductsAdapter?.notifyDataSetChanged()
    }

    fun getProductSelected(): MutableLiveData<Products> {
        return selected
    }

    fun setProductSelected(index: Int) {
        selected.value = getProductAt(index)
    }

    fun getProductAt(position: Int): Products? {
        val products: List<Products>? = observable.getProducts().value
        return products?.get(position)
    }

    fun getCurrentUser(): MutableLiveData<FirebaseUser>? {
        return observable.getCurrentUser()
    }

    fun callCurrentUser() {
        observable.callCurrentUser()
    }

    fun getSignOut(): MutableLiveData<Boolean> {
        return observable.getSignOut()
    }

    fun callSignOut() {
        observable.callSignOut()
    }

    fun getLoading(): MutableLiveData<Boolean> {
        return observable.getLoading()
    }

    fun getMessage(): MutableLiveData<Int> {
        return observable.getMessageDialog()
    }

}

@BindingAdapter("imageUrl")
fun getImageProductAt(imageView: ImageView, imageUrl: String) {
    Picasso.get().load(imageUrl).resize(520, 520).centerInside().into(imageView)
}