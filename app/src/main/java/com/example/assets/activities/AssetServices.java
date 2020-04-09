package com.example.assets.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AppCompatActivity;

import com.mashape.unirest.http.Unirest;

import org.json.JSONArray;
import org.json.JSONObject;

public class AssetServices {

    public static JSONObject getCurrencyRates() throws Exception {
        String resp = Unirest.get("https://api.exchangeratesapi.io/latest?base=USD").asString().getBody();
        System.out.println("NETWORK REQUEST ###########################################################################################");
        return new JSONObject(resp).getJSONObject("rates");
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
        return rates;
    }

    public static boolean isConnected(AppCompatActivity activity) {
        ConnectivityManager cm =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
