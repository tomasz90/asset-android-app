package com.example.assets.util

import com.example.assets.util.client.CryptoProviderApiClient
import com.example.assets.util.client.CurrencyProviderApiClient
import com.example.assets.util.client.MetalsProviderApiClient
import com.example.assets.util.client.RatesResponse
import com.fasterxml.jackson.databind.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class RateFacade {

    private var mapper: ObjectMapper = ObjectMapper()

    init {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    private val jacksonConverter = JacksonConverterFactory.create(mapper)

    private val currencyProviderApiClient = Retrofit.Builder()
            .baseUrl("https://api.exchangerate.host/")
            .addConverterFactory(jacksonConverter)
            .build()
            .create(CurrencyProviderApiClient::class.java)

    private val cryptoProviderApiClient = Retrofit.Builder()
            .baseUrl("https://pro-api.coinmarketcap.com/")
            .addConverterFactory(jacksonConverter)
            .build()
            .create(CryptoProviderApiClient::class.java)

    private val metalProviderApiClient = Retrofit.Builder()
            .baseUrl("https://www.moneymetals.com/")
            .addConverterFactory(jacksonConverter)
            .build()
            .create(MetalsProviderApiClient::class.java)

    fun getCurrencies(): Map<String, Float> {
        val response = currencyProviderApiClient.getRates().execute() as Response<RatesResponse>
        return response.handle()
    }

    fun getCryptos(): Map<String, Float> {
        val response = cryptoProviderApiClient.getRates().execute() as Response<RatesResponse>
        return response.handle()
    }

    fun getMetals(): Map<String, Float> {
        val response = metalProviderApiClient.getRates().execute() as Response<RatesResponse>
        return response.handle()
    }

    fun getRates(): Map<String, Float> {
        return getCryptos() + getCurrencies() + getMetals()
    }

    private fun Response<RatesResponse>.handle(): Map<String, Float> {
        if (this.isSuccessful) {
            return this.body()!!.toRates()
        } else {
            throw Exception() //todo handle this
        }
    }
}