package com.placetopay.commerce.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.placetopay.commerce.BR
import com.placetopay.commerce.model.Products
import com.placetopay.commerce.viewmodel.ProductsViewModel

class RecyclerProductsAdapter(var productsViewModel: ProductsViewModel, var resource: Int) :
    RecyclerView.Adapter<RecyclerProductsAdapter.CardProductHolder>() {

    private var products: List<Products>? = null

    fun setProductsList(productsList: List<Products>) {
        this.products = productsList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardProductHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        var binding: ViewDataBinding =
            DataBindingUtil.inflate(layoutInflater, viewType, parent, false)
        return CardProductHolder(binding)
    }

    override fun getItemCount(): Int {
        return products?.size ?: 0
    }

    override fun onBindViewHolder(holder: CardProductHolder, position: Int) {
        holder.setDataCard(productsViewModel, position)
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    fun getLayoutIdForPosition(position: Int): Int {
        return resource
    }

    class CardProductHolder(viewDataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        private var viewDataBinding: ViewDataBinding? = null

        init {
            this.viewDataBinding = viewDataBinding
        }

        fun setDataCard(productsViewModel: ProductsViewModel, position: Int) {
            viewDataBinding?.setVariable(BR.model, productsViewModel)
            viewDataBinding?.setVariable(BR.position, position)
            viewDataBinding?.executePendingBindings()
        }
    }

}