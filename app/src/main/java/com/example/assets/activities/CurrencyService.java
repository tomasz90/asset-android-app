package com.example.assets.activities;

import com.mashape.unirest.http.Unirest;

import org.json.JSONObject;

public class CurrencyService {

    public static JSONObject getRates() throws Exception {
        String resp = Unirest.get("https://api.exchangeratesapi.io/latest?base=USD").asString().getBody();
        System.out.println("NETWORK REQUEST ###########################################################################################");
        return new JSONObject(resp).getJSONObject("rates");
    }
}
