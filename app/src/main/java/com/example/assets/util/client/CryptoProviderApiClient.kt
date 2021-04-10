package com.example.assets.util.client

import com.example.assets.util.CryptoRates
import com.example.assets.util.Rate
import com.fasterxml.jackson.annotation.JsonProperty
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CryptoProviderApiClient {

    @Headers("X-CMC_PRO_API_KEY: dfff4181-377d-4dfe-8b50-52245c63eb05")
    @GET("v1/cryptocurrency/listings/latest")
    fun getRates(@Query("limit") limit: Int = 400): Call<CryptoRatesResponse>
}

class CryptoRatesResponse(@JsonProperty("data") val data: List<CryptoData>): RatesResponse {

    override fun toRates(): CryptoRates =
            CryptoRates(data.map { Rate(it.symbol, it.quote.usd.price) })

}

class CryptoData(@JsonProperty("symbol") val symbol: String,
                 @JsonProperty("quote") val quote: Quote)

class Quote(@JsonProperty("USD") val usd: USD)

class USD(@JsonProperty("price") val price: Float)