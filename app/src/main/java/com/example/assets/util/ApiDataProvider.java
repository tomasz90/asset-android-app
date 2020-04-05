package com.example.assets.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assets.activities.CurrencyService;
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
            JSONObject currencyRates = CurrencyService.getRates();
            JSONObject allRates = new JSONObject().put(key, currencyRates);
            return allRates.getJSONObject(key);
        }
    };

    private static LoadingCache<String, JSONObject> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build(loader);

    public ApiDataProvider(AppCompatActivity activity) {
        ApiDataProvider.activity = activity;
    }

    public void populateTextViews(boolean withCleanCache, DataUpdater updater) {
        boolean isConnected = isConnected();
        if (withCleanCache) {
            if (isConnected) {
                cache.invalidate(currencies);
            } else {
                displayToast();
                return;
            }
        } else {
            if (!isConnected && (cache.getIfPresent(currencies) == null))  {
                displayToast();
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
                if (!result.keys().hasNext()) {
                    displayToast();
                } else {
                    updater.updateUI(result);
                }
            }
        };
    }

    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private static void displayToast() {
        Toast toast = Toast.makeText(activity, "Network not available :(", Toast.LENGTH_SHORT);
        toast.show();
    }
}
