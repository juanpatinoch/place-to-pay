package com.placetopay.commerce.model.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.*

interface ApiServicePlaceToPay {

    @POST("gateway/information")
    fun getInformation(@Body data: HashMap<String, Any?>): Call<JsonObject>

    @POST("gateway/process")
    fun process(@Body data: HashMap<String, Any?>): Call<JsonObject>

    @POST("gateway/query")
    fun query(@Body data: HashMap<String, Any?>): Call<JsonObject>

}