package com.example.assets.util;

import android.app.Application;
import android.os.AsyncTask;

import com.example.assets.constants.Constants;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import lombok.SneakyThrows;

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
        getData(withCleanCache, null, updater);
    }

    public void getData(boolean withCleanCache, String assetType, DataUpdater updater) {
        boolean isConnected = AssetServices.isConnected(application);
        if (withCleanCache) {
            if (isConnected) {
                cache.invalidateAll();
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
        getAsync(updater, assetType).execute();
    }

    private static AsyncTask<String, Void, JSONObject> getAsync(DataUpdater updater, String assetType) {
        return new AsyncTask<String, Void, JSONObject>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @SneakyThrows
            @Override
            protected JSONObject doInBackground(String... strings) {
                if (assetType == null) {
                    return new JSONObject()
                            .put(Constants.CURRENCIES, cache.getUnchecked(Constants.CURRENCIES))
                            .put(Constants.CRYPTOS, cache.getUnchecked(Constants.CRYPTOS))
                            .put(Constants.METALS, cache.getUnchecked(Constants.METALS));
                }
                return cache.getUnchecked(assetType);
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
