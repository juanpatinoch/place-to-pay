package com.placetopay.commerce.model.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.placetopay.commerce.R
import com.placetopay.commerce.model.Transactions
import com.placetopay.commerce.util.Commons

class PaymentListRepository {

    private var firebaseAuth: FirebaseAuth? = null
    private var firebaseFirestore: FirebaseFirestore? = null
    private var transactions = MutableLiveData<List<Transactions>>()
    private var loading = MutableLiveData<Boolean>()
    private var message = MutableLiveData<Int>()

    fun getTransactions(): MutableLiveData<List<Transactions>> {
        return transactions
    }

    fun callTransactions() {
        if (firebaseAuth == null)
            firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseFirestore == null)
            firebaseFirestore = FirebaseFirestore.getInstance()

        firebaseFirestore?.collection("transactions")
            ?.whereEqualTo("email", firebaseAuth?.currentUser?.email)
            ?.orderBy("date", Query.Direction.DESCENDING)
            ?.addSnapshotListener { result, e ->
                val transactionsList = ArrayList<Transactions>()
                if (e != null) {
                    Log.e("Error", e.message)
                    loading.value = false
                    message.value = R.string.home_message_error
                    return@addSnapshotListener
                } else if (result != null && !result.isEmpty) {
                    for (item in result.documents) {
                        val transaction = Transactions()
                        transaction.id = item.id
                        transaction.status = if (item.data?.get("status") == null) null else item.data?.get("status").toString()
                        transaction.currency = if (item.data?.get("currency") == null) null else item.data?.get("currency").toString()
                        transaction.date = if (item.data?.get("date") == null) null else item.data?.get("date").toString()
                        transaction.franchiseName = if (item.data?.get("franchiseName") == null) null else item.data?.get("franchiseName").toString()
                        transaction.internalReference = if (item.data?.get("internalReference") == null) null else item.data?.get("internalReference").toString()
                        transaction.message = if (item.data?.get("message") == null) null else item.data?.get("message").toString()
                        transaction.reference = if (item.data?.get("reference") == null) null else item.data?.get("reference").toString()
                        transaction.total = if (item.data?.get("total") == null) null else item.data?.get("total").toString().toInt()
                        transaction.price = if (item.data?.get("total") == null) null else Commons.getCurrencyFormat(item.data?.get("total").toString().toLong())

                        transactionsList.add(transaction)
                    }
                }
                transactions.value = transactionsList
            }
    }

    fun deleteTransaction(id: String) {
        if (firebaseFirestore == null)
            firebaseFirestore = FirebaseFirestore.getInstance()

        firebaseFirestore?.collection("transactions")?.document(id)?.delete()
            ?.addOnSuccessListener {

            }?.addOnFailureListener {

            }
    }

}