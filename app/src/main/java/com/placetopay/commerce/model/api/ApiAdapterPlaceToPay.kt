package com.placetopay.commerce.model.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiAdapterPlaceToPay {

    val urlAPI = "https://dev.placetopay.com/rest/"

    fun getClientService(): ApiServicePlaceToPay {

        val retrofit = Retrofit.Builder()
            .baseUrl(urlAPI)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiServicePlaceToPay::class.java)
    }
}