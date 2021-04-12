package com.example.assets.api

import com.example.assets.api.client.CryptoProviderApiClient
import com.example.assets.api.client.CurrencyProviderApiClient
import com.example.assets.api.client.MetalsProviderApiClient
import com.example.assets.api.client.RatesResponse
import com.fasterxml.jackson.databind.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class RateFacade(private val currencyProviderApiClient: CurrencyProviderApiClient,
                 private val cryptoProviderApiClient: CryptoProviderApiClient,
                 private val metalsProviderApiClient: MetalsProviderApiClient) {

    fun getRates(): Map<String, Float> {
        return getCryptos() + getCurrencies() + getMetals()
    }

    private fun getCurrencies(): Map<String, Float> {
        val response = currencyProviderApiClient.getRates().execute() as Response<RatesResponse>
        return response.handle()
    }

    private fun getCryptos(): Map<String, Float> {
        val response = cryptoProviderApiClient.getRates().execute() as Response<RatesResponse>
        return response.handle()
    }

    private fun getMetals(): Map<String, Float> {
        val response = metalsProviderApiClient.getRates().execute() as Response<RatesResponse>
        return response.handle()
    }

    private fun Response<RatesResponse>.handle(): Map<String, Float> {
        if (this.isSuccessful) {
            return this.body()!!.toRates()
        } else {
            throw Exception() //todo handle this
        }
    }
}