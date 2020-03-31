package com.example.assets.util;

import android.os.AsyncTask;

import com.example.assets.activities.CurrencyService;

import org.json.JSONObject;

import lombok.SneakyThrows;

public class GetJSONTask extends AsyncTask<String, Void, JSONObject> {

    public static JSONObject object;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @SneakyThrows
    @Override
    protected JSONObject doInBackground(String... strings) {
        return CurrencyService.getRates();
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        object = result;
    }
}
