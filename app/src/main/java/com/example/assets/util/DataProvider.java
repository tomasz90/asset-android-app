package com.example.assets.util;

import android.os.AsyncTask;

import com.example.assets.activities.CurrencyService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import lombok.SneakyThrows;

public class DataProvider {

    private static String currencies = "currencies";

    private static CacheLoader<String, JSONObject> loader = new CacheLoader<String, JSONObject>() {
        @Override
        public JSONObject load(String key) throws Exception {
            JSONObject object = new JSONObject().put(key, CurrencyService.getRates());
            return object.getJSONObject(key);
        }
    };

    private static LoadingCache<String, JSONObject> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build(loader);

    public static void execute(boolean withCleanCache, DataUpdater updater) {
        if(withCleanCache) {
            cache.invalidateAll();
        }

        new AsyncTask<String, Void, JSONObject>(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected JSONObject doInBackground(String... strings) {
                return cache.getUnchecked(currencies);
            }
            @SneakyThrows
            @Override
            protected void onPostExecute(JSONObject result) {
                updater.updateUI(result);

        }}.execute();
    }
}
