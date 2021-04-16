package com.example.assets

import com.example.assets.api.RateFacade
import com.example.assets.api.client.CryptoProviderApiClient
import com.example.assets.api.client.CurrencyProviderApiClient
import com.example.assets.api.client.MetalsProviderApiClient
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit.SECONDS


class AppContainer private constructor() {

    companion object {
        val appContainer = AppContainer()
    }

    private val mapper: ObjectMapper = ObjectMapper()

    init {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    private val jacksonConverter = JacksonConverterFactory.create(mapper)

    private val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(20, SECONDS)
            .writeTimeout(20, SECONDS)
            .readTimeout(20, SECONDS)
            .build()

    private val currencyProviderApiClient = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.exchangerate.host/")
            .addConverterFactory(jacksonConverter)
            .build()
            .create(CurrencyProviderApiClient::class.java)

    private val cryptoProviderApiClient = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://pro-api.coinmarketcap.com/")
            .addConverterFactory(jacksonConverter)
            .build()
            .create(CryptoProviderApiClient::class.java)

    private val metalProviderApiClient = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://www.moneymetals.com/")
            .addConverterFactory(jacksonConverter)
            .build()
            .create(MetalsProviderApiClient::class.java)

    val rateFacade = RateFacade(currencyProviderApiClient, cryptoProviderApiClient, metalProviderApiClient)

}