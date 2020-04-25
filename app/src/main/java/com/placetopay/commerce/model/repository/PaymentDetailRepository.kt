package com.placetopay.commerce.model.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.JsonObject
import com.placetopay.commerce.R
import com.placetopay.commerce.model.Transactions
import com.placetopay.commerce.model.api.ApiAdapterPlaceToPay
import com.placetopay.commerce.util.Commons
import com.placetopay.commerce.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class PaymentDetailRepository {

    private var transaction = MutableLiveData<Transactions>()
    private var loading = MutableLiveData<Boolean>()
    private var message = MutableLiveData<Int>()

    private var firebaseFirestore: FirebaseFirestore? = null

    fun getMessage(): MutableLiveData<Int> {
        return message
    }

    fun getLoading(): MutableLiveData<Boolean> {
        return loading
    }

    fun getRefreshStatus(): MutableLiveData<Transactions> {
        return transaction
    }

    fun callRefreshStatus(transaction: Transactions) {
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

            params["auth"] = auth
            params["internalReference"] = transaction.internalReference

            val apiAdapter = ApiAdapterPlaceToPay()
            val apiService = apiAdapter.getClientService()
            val call = apiService.query(params)

            call.enqueue(object : Callback<JsonObject> {
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    loading.value = false
                    message.value = R.string.payment_detail_message_error
                }

                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    try {
                        loading.value = false
                        val body = response.body()
                        val errorBody = response.errorBody()

                        if (errorBody != null) {
                            loading.value = false
                            message.value = R.string.payment_detail_message_error
                        } else if (body != null) {
                            val jsonObjectStatus = body.getAsJsonObject("status")
                            transaction.status =
                                if (jsonObjectStatus.get("status").isJsonNull) null else jsonObjectStatus.get(
                                    "status"
                                ).asString
                            transaction.message =
                                if (jsonObjectStatus.get("message").isJsonNull) null else jsonObjectStatus.get(
                                    "message"
                                ).asString
                            updateTransactionStatus(transaction)
                        }
                    } catch (e: Exception) {
                        loading.value = false
                        message.value = R.string.payment_detail_message_error
                    }
                }
            })
        } catch (e: Exception) {
            loading.value = false
            message.value = R.string.payment_detail_message_error
        }
    }

    private fun updateTransactionStatus(transaction: Transactions) {
        try {
            if (firebaseFirestore == null)
                firebaseFirestore = FirebaseFirestore.getInstance()

            firebaseFirestore?.collection("transactions")?.document(transaction.id.toString())
                ?.update(
                    mapOf(
                        "status" to transaction.status,
                        "message" to transaction.message
                    )
                )?.addOnSuccessListener {
                    loading.value = false
                    message.value = R.string.payment_detail_message_sucess
                    this.transaction.value = transaction
                }
                ?.addOnFailureListener {
                    loading.value = false
                    message.value = R.string.payment_detail_message_error
                }
        } catch (e: Exception) {
            loading.value = false
            message.value = R.string.payment_detail_message_error
        }
    }

}