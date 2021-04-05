package com.example.assets.util

abstract class Rates(open var rates: List<Rate>) {

    open lateinit var filterTo: List<String>

    fun filter(): Rates {
        rates = rates.filter { filterTo.contains(it.symbol) }
        return this
    }
}

class CurrencyRates(override var rates: List<Rate>) : Rates(rates) {
    override var filterTo = Currencies.values().map { it.toString() }
}

class CryptoRates(override var rates: List<Rate>) : Rates(rates) {
    override var filterTo = Cryptos.values().map { it.toString() }
}

class MetalRates(override var rates: List<Rate>) : Rates(rates) {
    override var filterTo = Metals.values().map { it.toString() }
}

class Rate(val symbol: String, val value: Float)

enum class Currencies { EUR, USD, CHF, JPY, GBP, PLN, NOK, SEK, DKK }
enum class Cryptos { BTC, ETH, LTC, XRP, ADA, DOT, LINK, MATIC, AVAX, ATOM, UTK, REEF }
enum class Metals { Gold, Silver, Platinum, Palladium }
