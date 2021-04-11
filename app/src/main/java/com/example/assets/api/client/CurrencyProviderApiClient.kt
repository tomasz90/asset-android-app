package com.example.assets.api.client

import com.fasterxml.jackson.annotation.JsonProperty
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyProviderApiClient {

    @GET("latest")
    fun getRates(@Query("base") base: String = "USD"): Call<CurrencyRatesResponse>
}

class CurrencyRatesResponse(@JsonProperty("rates") val rates: Map<String, Float>) : RatesResponse {

    override fun toRates(): Map<String, Float> =
            rates.mapValues { 1 / it.value }
                    .filter { it -> Currencies.values().map { it.toString() }.contains(it.key) }
}

enum class Currencies { EUR, USD, CHF, JPY, GBP, PLN, NOK, SEK, DKK }