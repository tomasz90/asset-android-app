package com.example.assets.util;

import android.os.AsyncTask;

import com.example.assets.activities.CurrencyService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import lombok.SneakyThrows;

public class DataProvider extends AsyncTask<String, Void, JSONObject> {

    private DataUpdater updater;
    private String key = "currencies";

    static CacheLoader<String, JSONObject> loader = new CacheLoader<String, JSONObject>() {
        @Override
        public JSONObject load(String key) throws Exception {
            JSONObject object = new JSONObject().put(key, CurrencyService.getRates());
            return object.getJSONObject(key);
        }
    };

    static LoadingCache<String, JSONObject> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .build(loader);

    public DataProvider(DataUpdater updater) {
        this.updater = updater;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @SneakyThrows
    @Override
    protected JSONObject doInBackground(String... strings) {
        return cache.getUnchecked(key);
    }

    @SneakyThrows
    @Override
    protected void onPostExecute(JSONObject result) {
        updater.updateUI(result);
    }
}
