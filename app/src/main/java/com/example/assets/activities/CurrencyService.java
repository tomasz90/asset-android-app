package com.example.assets.activities;

import android.os.AsyncTask;

import com.mashape.unirest.http.Unirest;

import org.json.JSONObject;

public class CurrencyService {

    public static String getRate() throws Exception {
        String resp = Unirest.get("https://api.exchangeratesapi.io/latest?base=USD").asString().getBody();
        return new JSONObject(resp).getJSONObject("rates").get("CHF").toString();
    }
}