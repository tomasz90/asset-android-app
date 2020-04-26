package com.example.assets.util;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.assets.constants.Constants;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AssetServices {

    public static JSONObject getCurrencyRates() throws Exception {
        String resp = Unirest.get("https://api.exchangeratesapi.io/latest?base=USD").asString().getBody();
        System.out.println("NETWORK REQUEST ###########################################################################################");
        return filterCurrencies(new JSONObject(resp).getJSONObject("rates"));
    }

    public static JSONObject getCryptoRates() throws Exception {
        String resp = Unirest.get("https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest")
                .header("X-CMC_PRO_API_KEY", "dfff4181-377d-4dfe-8b50-52245c63eb05").asString().getBody();
        JSONObject rates = new JSONObject();
        JSONArray array = new JSONObject(resp).getJSONArray("data");
        for (int i = 0; i < array.length(); i++) {
            String symbol = array.getJSONObject(i).getString("symbol");
            String rate = array.getJSONObject(i).getJSONObject("quote").getJSONObject("USD").get("price").toString();
            rates.put(symbol, rate);
        }
        return filterCryptos(rates);
    }

    public static JSONObject getMetalRates() throws UnirestException, JSONException {
        String resp = Unirest.get("https://www.moneymetals.com/ajax/spot-prices").asString().getBody();
        JSONObject rates = new JSONObject(resp);
        return filterMetals(rates);
    }

    public static boolean isConnected(Application activity) {
        ConnectivityManager cm =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    static JSONObject filterCurrencies(JSONObject raw) throws JSONException {
        JSONObject filtered = new JSONObject();
        for (String currency : Constants.ALL_CURRENCIES) {
            filtered.put(currency, raw.get(currency));
        }
        return filtered;
    }

    static JSONObject filterCryptos(JSONObject raw) throws JSONException {
        JSONObject filtered = new JSONObject();
        for (String crypto : Constants.ALL_CRYPTOS) {
            filtered.put(crypto, raw.getString(crypto));
        }
        return filtered;
    }

    static JSONObject filterMetals(JSONObject raw) throws JSONException {
        JSONObject filteredRates = new JSONObject();
        for (String metal : Constants.ALL_METALS) {
            filteredRates.put(metal, raw.getJSONObject(metal)
                    .getString("price")
                    .replace("$", "")
                    .replace(",", ""));
        }
        return filteredRates;
    }
}
