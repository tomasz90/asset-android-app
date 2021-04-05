package com.example.assets.util.client

import com.example.assets.util.CurrencyRates
import com.example.assets.util.Rate
import com.fasterxml.jackson.annotation.JsonProperty
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyProviderApiClient {

    @GET("latest")
    fun getRates(@Query("base") base: String = "USD"): Call<CurrencyRatesResponse>
}

class CurrencyRatesResponse(@JsonProperty("rates") val rates: Map<String, Float>) : RatesResponse {

    override fun toRates() : CurrencyRates = CurrencyRates(rates.map { Rate(it.key, it.value) })
}