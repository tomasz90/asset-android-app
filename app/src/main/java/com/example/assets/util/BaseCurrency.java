package com.example.assets.util;

public class BaseCurrency {

    private static String symbol;
    private static float rate;
    private static BaseCurrency baseCurrency = new BaseCurrency("USD", 1f);

    public BaseCurrency(String symbol, float rate) {
        BaseCurrency.symbol = symbol;
        BaseCurrency.rate = rate;
    }

    public static BaseCurrency getBaseCurrency() {
        return baseCurrency;
    }

    public static void setBaseCurrency(BaseCurrency baseCurrency) {
        BaseCurrency.baseCurrency = baseCurrency;
    }

    public float getRate() {
        return rate;
    }
}
