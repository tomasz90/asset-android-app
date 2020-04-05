package com.example.assets.util;

import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assets.activities.AssetServices;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import lombok.SneakyThrows;

public class ApiDataProvider {

    private static String currencies = "currencies";
    private static AppCompatActivity activity;

    private static CacheLoader<String, JSONObject> loader = new CacheLoader<String, JSONObject>() {
        @Override
        public JSONObject load(String key) throws Exception {
            JSONObject currencyRates = AssetServices.getCurrencyRates();
            JSONObject allRates = new JSONObject().put(key, currencyRates);
            return allRates.getJSONObject(key);
        }
    };

    private static LoadingCache<String, JSONObject> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(loader);

    public ApiDataProvider(AppCompatActivity activity) {
        ApiDataProvider.activity = activity;
    }

    public void populateTextViews(boolean withCleanCache, DataUpdater updater) {
        boolean isConnected = AssetServices.isConnected(activity);
        if (withCleanCache) {
            if (isConnected) {
                cache.invalidate(currencies);
            } else {
                ToastManager.displayToast(activity);
                return;
            }
        } else {
            if (!isConnected && (cache.getIfPresent(currencies) == null)) {
                ToastManager.displayToast(activity);
                return;
            }
        }
        getAsync(updater).execute();
    }

    private static AsyncTask<String, Void, JSONObject> getAsync(DataUpdater updater) {
        return new AsyncTask<String, Void, JSONObject>() {

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
            }
        };
    }

}
