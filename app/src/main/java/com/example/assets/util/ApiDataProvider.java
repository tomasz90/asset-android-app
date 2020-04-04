package com.example.assets.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assets.R;
import com.example.assets.activities.CurrencyService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import lombok.SneakyThrows;

public class ApiDataProvider {

    private static String currencies = "currencies";
    private AppCompatActivity activity;

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

    public ApiDataProvider(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void populateTextViews(boolean withCleanCache, DataUpdater updater) {
        if (withCleanCache) {
            if (isConnected()) {
                cache.invalidate(currencies);
            } else {
               Toast toast =  Toast.makeText(activity, "Network not available :(", Toast.LENGTH_SHORT);
               toast.show();
            }
        }

        new AsyncTask<String, Void, JSONObject>() {

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
        }.execute();
    }

    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
