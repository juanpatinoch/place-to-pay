package com.placetopay.commerce.viewmodel

import android.text.TextWatcher
import android.util.Patterns
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.placetopay.commerce.R
import com.placetopay.commerce.model.dto.PayProduct
import com.placetopay.commerce.model.dto.Transactions
import com.placetopay.commerce.model.observable.PayProductObservable
import com.placetopay.commerce.util.CreditCardDateFormattingTextWatcher
import com.placetopay.commerce.util.CreditCardNumberFormattingTextWatcher


class PayProductViewModel : ViewModel() {

    var productName = MutableLiveData<String>()
    var productPrice = MutableLiveData<String>()
    var productPriceValue = MutableLiveData<Int>()
    var payerName = MutableLiveData<String>()
    var payerEmail = MutableLiveData<String>()
    var payerCellphone = MutableLiveData<String>()
    var creditCardNumber = MutableLiveData<String>()
    var creditCardExpirationDate = MutableLiveData<String>()
    var creditCardCVV = MutableLiveData<String>()

    private var observable = PayProductObservable()
    private var close = MutableLiveData<Boolean>()

    fun getCreditCardNumberTextWatcher(): TextWatcher {
        return CreditCardNumberFormattingTextWatcher()
    }

    fun getCreditCardDateTextWatcher() : TextWatcher{
        return CreditCardDateFormattingTextWatcher()
    }

    fun getClose(): MutableLiveData<Boolean> {
        return close
    }

    fun setClose() {
        close.value = true
    }

    fun callCurrentUser() {
        observable.callCurrentUser()
    }

    fun getCurrentUser(): MutableLiveData<FirebaseUser> {
        return observable.getCurrentUser()
    }

    fun getTransaction(): MutableLiveData<Transactions> {
        return observable.getTransaction()
    }

    fun getMessage(): MutableLiveData<Int> {
        return observable.getMessage()
    }

    fun getLoading(): MutableLiveData<Boolean> {
        return observable.getLoading()
    }

    fun onPayClick() {
        val isCreditCardNumberValid = !creditCardNumber.value.isNullOrEmpty()
        val isCreditCardExpirationDateValid = !creditCardExpirationDate.value.isNullOrEmpty()
        val isCreditCardCVVValid = !creditCardCVV.value.isNullOrEmpty()

        val isNameValid = !payerName.value.isNullOrEmpty()
        val isCellphoneValid = !payerCellphone.value.isNullOrEmpty()
        var isEmailValid = !payerEmail.value.isNullOrEmpty()
        if (isEmailValid)
            isEmailValid = Patterns.EMAIL_ADDRESS.matcher(payerEmail.value).matches()

        if (!isNameValid or !isEmailValid or !isCellphoneValid or !isCreditCardNumberValid or !isCreditCardExpirationDateValid or !isCreditCardCVVValid)
            observable.setMessage(R.string.pay_product_message_validateion)
        else {
            val payProduct = PayProduct()
            payProduct.productName = productName.value
            payProduct.productPrice = productPrice.value
            payProduct.productPriceValue = productPriceValue.value
            payProduct.payerName = payerName.value
            payProduct.payerEmail = payerEmail.value
            payProduct.payerCellphone = payerCellphone.value
            payProduct.creditCardNumber = creditCardNumber.value
            payProduct.creditCardExpirationDate = creditCardExpirationDate.value
            payProduct.creditCardCVV = creditCardCVV.value

            observable.payProduct(payProduct)
        }

    }
}

@BindingAdapter("textChangedListener")
fun bindTextWatcher(editText: EditText, textWatcher: TextWatcher?) {
    editText.addTextChangedListener(textWatcher)
}