package com.example.assets.util.client

import com.example.assets.util.Rates

interface RatesResponse {

    fun toRates(): Rates
}