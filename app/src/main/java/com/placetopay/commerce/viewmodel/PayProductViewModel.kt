package com.placetopay.commerce.viewmodel

import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.placetopay.commerce.model.observable.PayProductObservable
import com.placetopay.commerce.util.CreditCardDateFormattingTextWatcher
import com.placetopay.commerce.util.CreditCardNumberFormattingTextWatcher


class PayProductViewModel : ViewModel() {

    var payProductObservable = PayProductObservable()

    var productName = MutableLiveData<String>()
    var productPrice = MutableLiveData<String>()

    var payerName = MutableLiveData<String>()
    var payerEmail = MutableLiveData<String>()
    var payerCellphone = MutableLiveData<String>()

    var closeActivity = MutableLiveData<Boolean>()

    val textWatcherCreditCardNumber = CreditCardNumberFormattingTextWatcher()
    val textWatcherCreditCardExpirationDate = CreditCardDateFormattingTextWatcher()

    fun onCloseActivityClick() {
        closeActivity.value = true
    }

    fun onProcessPaymentClick() {
        val isNameValid = !payerName.value.isNullOrEmpty()
        var isEmailValid = !payerEmail.value.isNullOrEmpty()
        val isCellphoneValid = !payerCellphone.value.isNullOrEmpty()

        if (isEmailValid)
            isEmailValid = !Patterns.EMAIL_ADDRESS.matcher(payerEmail.value).matches()
    }

    fun callCurrentUser() {
        payProductObservable.callCurrentUser()
    }

    fun getCurrentUser(): MutableLiveData<FirebaseUser> {
        return payProductObservable.getCurrentUser()
    }
}

@BindingAdapter("textChangedListener")
fun bindTextWatcher(editText: EditText, textWatcher: TextWatcher?) {
    editText.addTextChangedListener(textWatcher)
}