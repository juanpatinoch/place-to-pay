package com.placetopay.commerce.viewmodel

import android.text.TextWatcher
import android.util.Patterns
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.placetopay.commerce.model.Transactions
import com.placetopay.commerce.model.observable.PayProductObservable
import com.placetopay.commerce.util.CreditCardDateFormattingTextWatcher
import com.placetopay.commerce.util.CreditCardNumberFormattingTextWatcher


class PayProductViewModel : ViewModel() {

    private var payProductObservable = PayProductObservable()
    var productName = MutableLiveData<String>()
    var productPrice = MutableLiveData<String>()
    var productPriceValue = MutableLiveData<Int>()
    var payerName = MutableLiveData<String>()
    var payerEmail = MutableLiveData<String>()
    var payerCellphone = MutableLiveData<String>()
    var creditCardNumber = MutableLiveData<String>()
    var creditCardExpirationDate = MutableLiveData<String>()
    var creditCardCVV = MutableLiveData<String>()
    var closeActivity = MutableLiveData<Boolean>()
    var showValidationMessage = MutableLiveData<Boolean>()
    val textWatcherCreditCardNumber = CreditCardNumberFormattingTextWatcher()
    val textWatcherCreditCardExpirationDate = CreditCardDateFormattingTextWatcher()

    fun onCloseActivityClick() {
        closeActivity.value = true
    }

    fun onProcessPaymentClick() {
        val isCreditCardNumberValid = !creditCardNumber.value.isNullOrEmpty()
        val isCreditCardExpirationDateValid = !creditCardExpirationDate.value.isNullOrEmpty()
        val isCreditCardCVVValid = !creditCardCVV.value.isNullOrEmpty()

        val isNameValid = !payerName.value.isNullOrEmpty()
        val isCellphoneValid = !payerCellphone.value.isNullOrEmpty()
        var isEmailValid = !payerEmail.value.isNullOrEmpty()
        if (isEmailValid)
            isEmailValid = Patterns.EMAIL_ADDRESS.matcher(payerEmail.value).matches()

        if (!isNameValid or !isEmailValid or !isCellphoneValid or !isCreditCardNumberValid or !isCreditCardExpirationDateValid or !isCreditCardCVVValid)
            showValidationMessage.value = true
        else {
            payProductObservable.payProduct(
                productName.value.toString(),
                productPriceValue.value!!,
                payerName.value.toString(),
                payerEmail.value.toString(),
                payerCellphone.value.toString(),
                creditCardNumber.value.toString(),
                creditCardExpirationDate.value.toString(),
                creditCardCVV.value.toString()
            )
        }

    }

    fun callCurrentUser() {
        payProductObservable.callCurrentUser()
    }

    fun getCurrentUser(): MutableLiveData<FirebaseUser> {
        return payProductObservable.getCurrentUser()
    }

    fun getTransaction(): MutableLiveData<Transactions> {
        return payProductObservable.getTransaction()
    }

    fun getMessage(): MutableLiveData<Int> {
        return payProductObservable.getMessage()
    }

    fun getLoading(): MutableLiveData<Boolean> {
        return payProductObservable.getLoading()
    }
}

@BindingAdapter("textChangedListener")
fun bindTextWatcher(editText: EditText, textWatcher: TextWatcher?) {
    editText.addTextChangedListener(textWatcher)
}