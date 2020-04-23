package com.placetopay.commerce.model

import java.io.Serializable

class Products : Serializable {

    var code: String? = null
    var name: String? = null
    var description: String? = null
    var price: Int? = null
    var priceText: String? = null
    var image: String? = null
    var header: String? = null
    var discount: String? = null

}