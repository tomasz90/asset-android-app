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

    fun getCurrencies(): Rates {
        val response = currencyProviderApiClient.getRates().execute() as Response<RatesResponse>
        return response.handle()
    }

    fun getCryptos(): Rates {
        val response = cryptoProviderApiClient.getRates().execute() as Response<RatesResponse>
        return response.handle()
    }

    fun getMetals(): Rates {
        val response = metalProviderApiClient.getRates().execute() as Response<RatesResponse>
        return response.handle()
    }

//    fun getRates(type: Rates): List<Rate> {
//        return when (type) {
//            CryptoRates::class -> getCryptos().rates
//            CurrencyRates::class -> getCurrencies().rates
//            MetalRates::class -> getMetals().rates
//            else -> throw Exception() //todo handle this
//        }
//    }

    fun getRates(): List<Rate> {
        return getCryptos().rates + getCurrencies().rates + getMetals().rates
    }

    private fun Response<RatesResponse>.handle(): Rates {
        if (this.isSuccessful) {
            return this.body()!!.toRates().filter()
        } else {
            throw Exception() //todo handle this
        }
    }
}