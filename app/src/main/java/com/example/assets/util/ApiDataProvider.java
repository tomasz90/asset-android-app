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

import static com.example.assets.constants.AssetConstants.CRYPTOS;
import static com.example.assets.constants.AssetConstants.CURRENCIES;
import static com.example.assets.constants.AssetConstants.METALS;
import static com.example.assets.constants.Constants.MESSAGE_NETWORK_MISSING;

public class ApiDataProvider {

    private Application application;

    private static CacheLoader<String, JSONObject> loader = new CacheLoader<String, JSONObject>() {
        @Override
        public JSONObject load(String assetType) throws Exception {
            return AssetServices.getRates(assetType);
        }
    };

    private static LoadingCache<String, JSONObject> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(loader);

    public ApiDataProvider(Application application) {
        this.application = application;
    }

    public void getData(boolean withCleanCache, DataUpdater updater) {
        boolean isConnected = AssetServices.isConnected(application);
        if (withCleanCache) {
            if (isConnected) {
                cache.invalidateAll();
            } else {
                Dialog.displayToast(application, MESSAGE_NETWORK_MISSING);
                return;
            }
        } else {
            if (!isConnected && !isCacheDataAvailable()) {
                Dialog.displayToast(application, MESSAGE_NETWORK_MISSING);
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

            @SneakyThrows
            @Override
            protected JSONObject doInBackground(String... strings) {
                    return new JSONObject()
                            .put(CURRENCIES, cache.getUnchecked(CURRENCIES))
                            .put(CRYPTOS, cache.getUnchecked(CRYPTOS))
                            .put(METALS, cache.getUnchecked(METALS));
            }

            @SneakyThrows
            @Override
            protected void onPostExecute(JSONObject result) {
                updater.update(result);
            }
        };
    }

    private boolean isCacheDataAvailable() {
        return !(cache.getIfPresent(CURRENCIES) == null && cache.getUnchecked(CRYPTOS) == null && cache.getUnchecked(CRYPTOS) == null);
    }

    public interface DataUpdater {
        void update(JSONObject dataFromApi) throws JSONException;
    }
}
