package com.example.assets.api

import android.util.Log
import com.example.assets.api.client.CryptoProviderApiClient
import com.example.assets.api.client.CurrencyProviderApiClient
import com.example.assets.api.client.MetalsProviderApiClient
import com.example.assets.api.client.RatesResponse
import retrofit2.Response

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
        return if (this.isSuccessful) {
            this.body()!!.toRates()
        } else {
            Log.d("MY_LOG","ERROR! Response status is: "+ this.code())
            emptyMap()
        }
    }
}