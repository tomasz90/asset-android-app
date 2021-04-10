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

class MetalRatesResponse(@JsonProperty("Gold") val Gold: MetalData,
                         @JsonProperty("Silver") val Silver: MetalData,
                         @JsonProperty("Platinum") val Platinum: MetalData,
                         @JsonProperty("Palladium") val Palladium: MetalData,
                         @JsonProperty("Rhodium") val Rhodium: MetalData): RatesResponse {

    override fun toRates(): MetalRates {
        val rates = this::class.java.declaredFields.map { Rate(it.name, getPrice(it)) }
        return MetalRates(rates)
    }

    private fun getPrice(field: Field) = (field.get(this) as MetalData)
            .price
            .replace("$", "")
            .replace(",", "")
            .toFloat()
}

class MetalData(@JsonProperty("price") val price: String)
