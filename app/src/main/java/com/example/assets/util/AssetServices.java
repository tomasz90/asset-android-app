package com.example.assets.util;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.assets.constants.AssetConstants;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AssetServices {

    private static JSONObject getCurrencyRates() throws Exception {
        String resp = Unirest.get("https://api.exchangeratesapi.io/latest?base=USD").asString().getBody();
        return new JSONObject(resp).getJSONObject("rates");
    }

    private static JSONObject getCryptoRates() throws Exception {
        String resp = Unirest.get("https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest")
                .header("X-CMC_PRO_API_KEY", "dfff4181-377d-4dfe-8b50-52245c63eb05").asString().getBody();
        return new JSONObject(resp);
    }

    private static JSONObject getMetalRates() throws UnirestException, JSONException {
        String resp = Unirest.get("https://www.moneymetals.com/ajax/spot-prices").asString().getBody();
        return new JSONObject(resp);
    }

   public static JSONObject getRates(String assetType) throws Exception {
        switch (assetType) {
            case AssetConstants.CURRENCIES:
                return filterCurrencies(getCurrencyRates());
            case AssetConstants.CRYPTOS:
                return filterCryptos(parseCryptos(getCryptoRates()));
            case AssetConstants.METALS:
                return filterMetals(getMetalRates());
        }
        return null;
    }

    static JSONObject filterCurrencies(JSONObject raw) throws JSONException {
        JSONObject filtered = new JSONObject();
        for (String currency : AssetConstants.ALL_CURRENCIES) {
            filtered.put(currency, 1 / Utils.toFloat(raw.get(currency)));
        }
        return filtered;
    }

    static JSONObject filterCryptos(JSONObject raw) throws JSONException {
        JSONObject filtered = new JSONObject();
        for (String crypto : AssetConstants.ALL_CRYPTOS) {
            filtered.put(crypto, Utils.toFloat(raw.get(crypto)));
        }
        return filtered;
    }

    static JSONObject parseCryptos(JSONObject raw) throws JSONException {
        JSONArray data = raw.getJSONArray("data");
        JSONObject rates =  new JSONObject();
        for (int i = 0; i < data.length(); i++) {
            String symbol = data.getJSONObject(i).getString("symbol");
            String rate = data.getJSONObject(i).getJSONObject("quote").getJSONObject("USD").get("price").toString();
            rates.put(symbol, rate);
        }
        return rates;
    }

    static JSONObject filterMetals(JSONObject raw) throws JSONException {
        JSONObject filteredRates = new JSONObject();
        for (String metal : AssetConstants.ALL_METALS) {
            filteredRates.put(metal, Utils.toFloat(raw.getJSONObject(metal)
                    .getString("price")
                    .replace("$", "")
                    .replace(",", "")));
        }
        return filteredRates;
    }
}
