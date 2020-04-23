package com.placetopay.commerce.model.repository

import androidx.lifecycle.MutableLiveData
import com.placetopay.commerce.model.Products

class ProductsRepository {

    private var products = MutableLiveData<List<Products>>()

    fun getProducts(): MutableLiveData<List<Products>> {
        return products
    }

    fun callProducts() {
        var productsList = ArrayList<Products>()

        var productItem = Products()
        productItem.code = "8sd8sd77f"
        productItem.description = "Lorem inpsum"
        productItem.discountText = "-20%"
        productItem.imageUrl =
            "https://firebasestorage.googleapis.com/v0/b/lumarket-2.appspot.com/o/products%2Freebook_royal_glide_1.png?alt=media&token=23075b6c-a3ca-4e90-a0f1-b3ba8f58ace0"
        productItem.name = "Reebook Royal Glide"
        productItem.price = 199900

        productsList.add(productItem)

        var productItem2 = Products()
        productItem2.code = "87s66sdd"
        productItem2.description = "Is a smartwatch"
        productItem2.discountText = null
        productItem2.imageUrl =
            "https://firebasestorage.googleapis.com/v0/b/lumarket-2.appspot.com/o/products%2Fxiaomi_amazfit_bip_1.png?alt=media&token=20cebf06-b3be-409f-bc59-b695c7568bc1"
        productItem2.name = "Amazfit Bip"
        productItem2.price = 209950

        productsList.add(productItem2)

        var productItem3 = Products()
        productItem3.code = "kkfk29944mw"
        productItem3.description = "Delicioso refrigerio"
        productItem3.discountText = "Promo!"
        productItem3.imageUrl =
            "https://firebasestorage.googleapis.com/v0/b/lumarket-2.appspot.com/o/products%2Fdmartin_wrap.jpg?alt=media&token=66ab3eef-0b25-4296-88ab-88a1f04a0d3b"
        productItem3.name = "Wrap grande"
        productItem3.price = 7000

        productsList.add(productItem3)

        products.value = productsList
    }

}