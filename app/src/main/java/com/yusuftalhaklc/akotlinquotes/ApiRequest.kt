package com.yusuftalhaklc.akotlinquotes

import retrofit2.Call
import com.yusuftalhaklc.akotlinquotes.api.QuoteJson
import retrofit2.http.GET

interface ApiRequest {

    @GET("/random")
    fun getQuote() : Call<QuoteJson>

}