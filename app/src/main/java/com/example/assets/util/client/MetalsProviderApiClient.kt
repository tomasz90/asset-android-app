package com.example.assets.util.client

import com.example.assets.util.MetalRates
import com.example.assets.util.Rate
import com.fasterxml.jackson.annotation.JsonProperty
import retrofit2.Call
import retrofit2.http.GET
import java.lang.reflect.Field



interface MetalsProviderApiClient {

    @GET("ajax/spot-prices")
    fun getRates(): Call<MetalRatesResponse>
}

class MetalRatesResponse(@JsonProperty("Gold") val gold: MetalData,
                         @JsonProperty("Silver") val silver: MetalData,
                         @JsonProperty("Platinum") val platinum: MetalData,
                         @JsonProperty("Palladium") val palladium: MetalData,
                         @JsonProperty("Rhodium") val rhodium: MetalData): RatesResponse {

    override fun toRates(): MetalRates {
        val rates = this::class.java.declaredFields
                .map { Rate(it.name, getPrice(it)) }
        return MetalRates(rates)
    }

    private fun getPrice(field: Field) = (field.get(this) as MetalData)
            .price
            .replace("$", "")
            .replace(",", "")
            .toFloat()
}

class MetalData(@JsonProperty("price") val price: String)
