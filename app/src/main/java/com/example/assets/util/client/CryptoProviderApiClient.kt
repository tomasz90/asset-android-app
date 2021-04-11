package com.example.assets.util.client

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

    override fun toRates(): Map<String, Float> =
            data.map { it.symbol to it.quote.usd.price }
                    .toMap()
                    .filter { it -> Cryptos.values().map { it.toString() }.contains(it.key) }

}

class CryptoData(@JsonProperty("symbol") val symbol: String,
                 @JsonProperty("quote") val quote: Quote)

class Quote(@JsonProperty("USD") val usd: USD)

class USD(@JsonProperty("price") val price: Float)

enum class Cryptos { BTC, ETH, LTC, XRP, ADA, DOT, LINK, MATIC, AVAX, ATOM, UTK, REEF }