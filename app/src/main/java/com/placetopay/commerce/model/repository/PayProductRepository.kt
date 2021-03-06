package com.placetopay.commerce.model.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.JsonObject
import com.placetopay.commerce.R
import com.placetopay.commerce.model.dto.PayProduct
import com.placetopay.commerce.model.dto.Transactions
import com.placetopay.commerce.model.api.ApiAdapterPlaceToPay
import com.placetopay.commerce.util.Commons
import com.placetopay.commerce.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class PayProductRepository {

    private var firebaseAuth: FirebaseAuth? = null
    private var firebaseFirestore: FirebaseFirestore? = null

    private var firebaseUser = MutableLiveData<FirebaseUser>()
    private var transaction = MutableLiveData<Transactions>()
    private var message = MutableLiveData<Int>()
    private var loading = MutableLiveData<Boolean>()

    fun getMessage(): MutableLiveData<Int> {
        return message
    }

    fun setMessage(message: Int) {
        this.message.value = message
    }

    fun getLoading(): MutableLiveData<Boolean> {
        return loading
    }

    fun getTransaction(): MutableLiveData<Transactions> {
        return transaction
    }

    fun getCurrentUser(): MutableLiveData<FirebaseUser> {
        return firebaseUser
    }

    fun callCurrentUser() {
        if (firebaseAuth == null)
            firebaseAuth = FirebaseAuth.getInstance()

        firebaseUser.value = firebaseAuth?.currentUser
    }

    fun payProduct(payProduct: PayProduct) {
        try {
            loading.value = true
            val nonce = Commons.getRandom()
            val seed = Commons.getCurrentDateFormat()
            val nonceBase64 = Commons.getBase64(nonce.toByteArray())
            val tranKeyBase64 =
                Commons.getBase64(Commons.getSHA256(nonce + seed + Constants.placeToPaytranKey))

            val params = HashMap<String, Any?>()

            val auth = HashMap<String, Any?>()
            auth["login"] = Constants.placeToPayLogin
            auth["tranKey"] = tranKeyBase64
            auth["nonce"] = nonceBase64
            auth["seed"] = seed

            val payment = HashMap<String, Any?>()
            payment["reference"] = payProduct.productName

            val amount = HashMap<String, Any?>()
            amount["currency"] = "COP"
            amount["total"] = payProduct.productPriceValue

            payment["amount"] = amount

            val instrument = HashMap<String, Any?>()

            val card = HashMap<String, Any?>()
            card["number"] = payProduct.creditCardNumber?.replace(" ", "")
            card["expirationMonth"] = payProduct.creditCardExpirationDate?.split("/")?.get(0)
            card["expirationYear"] = payProduct.creditCardExpirationDate?.split("/")?.get(1)
            card["cvv"] = payProduct.creditCardCVV

            instrument["card"] = card

            val payer = HashMap<String, Any?>()
            payer["name"] = payProduct.payerName
            payer["email"] = payProduct.payerEmail
            payer["mobile"] = payProduct.payerCellphone

            params["auth"] = auth
            params["payment"] = payment
            params["ipAddress"] = Commons.getIPAddress(true)
            params["userAgent"] = System.getProperty("http.agent")
            params["instrument"] = instrument
            params["payer"] = payer
            params["buyer"] = payer

            val apiAdapter = ApiAdapterPlaceToPay()
            val apiService = apiAdapter.getClientService()
            val call = apiService.process(params)

            call.enqueue(object : Callback<JsonObject> {
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    loading.value = false
                    message.value = R.string.pay_product_message_error
                }

                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    try {
                        val body = response.body()
                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            loading.value = false
                            message.value = R.string.pay_product_message_error
                        } else if (body != null) {
                            val transaction =
                                Transactions()

                            val jsonObjectStatus = body.getAsJsonObject("status")
                            transaction.status =
                                if (jsonObjectStatus.get("status").isJsonNull) null else jsonObjectStatus.get(
                                    "status"
                                ).asString
                            transaction.message =
                                if (jsonObjectStatus.get("message").isJsonNull) null else jsonObjectStatus.get(
                                    "message"
                                ).asString

                            transaction.date =
                                if (body.get("date").isJsonNull) null else body.get("date").asString
                            transaction.internalReference =
                                if (body.get("internalReference").isJsonNull) null else body.get("internalReference").asString
                            transaction.reference =
                                if (body.get("reference").isJsonNull) null else body.get("reference").asString
                            transaction.franchiseName =
                                if (body.get("franchiseName").isJsonNull) null else body.get("franchiseName").asString

                            val jsonObjectAmount = body.getAsJsonObject("amount")

                            transaction.currency =
                                if (jsonObjectAmount.get("currency").isJsonNull) null else jsonObjectAmount.get(
                                    "currency"
                                ).asString
                            transaction.price =
                                if (jsonObjectAmount.get("total").isJsonNull) null else Commons.getCurrencyFormat(
                                    jsonObjectAmount.get("total").asLong
                                )
                            transaction.total =
                                if (jsonObjectAmount.get("total").isJsonNull) null else jsonObjectAmount.get(
                                    "total"
                                ).asInt

                            saveTransaction(transaction)
                        }
                    } catch (e: Exception) {
                        loading.value = false
                        message.value = R.string.pay_product_message_error
                    }
                }
            })
        } catch (e: Exception) {
            loading.value = false
            message.value = R.string.pay_product_message_error
        }
    }

    private fun saveTransaction(transaction: Transactions) {
        try {
            if (firebaseFirestore == null)
                firebaseFirestore = FirebaseFirestore.getInstance()

            if (firebaseAuth == null)
                firebaseAuth = FirebaseAuth.getInstance()

            firebaseFirestore?.collection("transactions")
                ?.add(
                    hashMapOf(
                        "email" to firebaseAuth?.currentUser?.email,
                        "status" to transaction.status,
                        "message" to transaction.message,
                        "date" to transaction.date,
                        "internalReference" to transaction.internalReference,
                        "reference" to transaction.reference,
                        "franchiseName" to transaction.franchiseName,
                        "currency" to transaction.currency,
                        "total" to transaction.total
                    )
                )
                ?.addOnSuccessListener {
                    loading.value = false
                    transaction.id = it.id
                    this.transaction.value = transaction
                }?.addOnFailureListener {
                    loading.value = false
                }
        } catch (e: Exception) {
            loading.value = false
            message.value = R.string.pay_product_message_error
        }
    }

}