package com.placetopay.commerce.model.dto

import java.io.Serializable

class Transactions : Serializable {
    var id: String? = null
    var status: String? = null
    var message: String? = null
    var date: String? = null
    var internalReference: String? = null
    var reference: String? = null
    var franchiseName: String? = null
    var currency: String? = null
    var price: String? = null
    var total: Int? = null
}