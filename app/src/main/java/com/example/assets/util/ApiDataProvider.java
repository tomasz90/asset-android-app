package com.example.assets.util;

import android.app.Application;
import android.os.AsyncTask;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import lombok.SneakyThrows;

public class ApiDataProvider {

    private static final String CURRENCIES = "currencies";
    private static final String CRYPTOS = "cryptos";
    private Application application;

    private static CacheLoader<String, JSONObject> loader = new CacheLoader<String, JSONObject>() {
        @Override
        public JSONObject load(String key) throws Exception {
            JSONObject currencyRates = AssetServices.getCurrencyRates();
            JSONObject cryptoRates = AssetServices.getCryptoRates();
            JSONObject allRates = new JSONObject()
                    .put(CURRENCIES, currencyRates)
                    .put(CRYPTOS, cryptoRates);
            return allRates.getJSONObject(key);
        }
    };

    private static LoadingCache<String, JSONObject> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(loader);

    public ApiDataProvider(Application application) {
        this.application = application;
    }

    public void getData(boolean withCleanCache, DataUpdater updater) {
        String assetType = CURRENCIES;
        boolean isConnected = AssetServices.isConnected(application);
        if (withCleanCache) {
            if (isConnected) {
                cache.invalidate(assetType);
            } else {
                ToastManager.displayToast(application);
                return;
            }
        } else {
            if (!isConnected && (cache.getIfPresent(assetType) == null)) {
                ToastManager.displayToast(application);
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
                return cache.getUnchecked(CURRENCIES);
            }

            @SneakyThrows
            @Override
            protected void onPostExecute(JSONObject result) {
                updater.update(result);
            }
        };
    }

    public interface DataUpdater {
        void update(JSONObject dataFromApi) throws JSONException;
    }
}
