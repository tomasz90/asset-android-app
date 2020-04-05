package com.example.assets.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AppCompatActivity;

import com.mashape.unirest.http.Unirest;

import org.json.JSONObject;

public class AssetServices {

    public static JSONObject getCurrencyRates() throws Exception {
        String resp = Unirest.get("https://api.exchangeratesapi.io/latest?base=USD").asString().getBody();
        System.out.println("NETWORK REQUEST ###########################################################################################");
        return new JSONObject(resp).getJSONObject("rates");
    }

    public static boolean isConnected(AppCompatActivity activity) {
        ConnectivityManager cm =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
