package com.placetopay.commerce.model

import java.io.Serializable

class Products : Serializable {

    var code: String? = null
    var name: String? = null
    var description: String? = null
    var price: Int? = null
    var imageUrl: String? = null
    var discountText: String? = null

}