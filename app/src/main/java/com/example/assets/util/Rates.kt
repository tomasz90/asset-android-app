package com.example.assets.util

abstract class Rates(open var rates: List<Rate>) {

    open lateinit var filterTo: List<String>

    fun filter(): Rates {
        rates = rates.filter { it.symbol.equals(filterTo) }
        return this
    }
}

class CurrencyRates(override var rates: List<Rate>) : Rates(rates) {
    override var filterTo = listOf("")
}

class CryptoRates(override var rates: List<Rate>) : Rates(rates) {
    override var filterTo = listOf("")
}

class MetalRates(override var rates: List<Rate>) : Rates(rates) {
    override var filterTo = listOf("")
}

class Rate(val symbol: String, val value: Float)
