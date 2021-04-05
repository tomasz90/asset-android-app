package com.example.assets.util

import com.example.assets.util.client.CryptoProviderApiClient
import com.example.assets.util.client.CurrencyProviderApiClient
import com.example.assets.util.client.MetalsProviderApiClient
import com.fasterxml.jackson.databind.*
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

    fun getCurrencies(): CurrencyRates {
        val response = currencyProviderApiClient.getRates().execute()
        if (response.isSuccessful) {
            return response.body()!!.toRates()
        } else {
            throw Exception() //todo handle this
        }
    }

    fun getCryptos(): CryptoRates {
        val response = cryptoProviderApiClient.getRates().execute()
        if (response.isSuccessful) {
            return response.body()!!.toRates()
        } else {
            throw Exception() //todo handle this
        }
    }

    fun getMetals(): MetalRates {
        val response = metalProviderApiClient.getRates().execute()
        if (response.isSuccessful) {
            return response.body()!!.toRates()
        } else {
            throw Exception() //todo handle this
        }
    }
}