package com.example.assets.util;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.example.assets.R;

import org.json.JSONObject;

import lombok.SneakyThrows;

import static com.example.assets.constants.AssetConstants.CRYPTOS;
import static com.example.assets.constants.AssetConstants.CURRENCIES;
import static com.example.assets.constants.AssetConstants.METALS;

public class ApiDataProvider {

    private final Application application;
    private static JSONObject jsonObject;

    public ApiDataProvider(Application application) {
        this.application = application;
    }

    public interface DataUpdater {
        void update(JSONObject apiRates);
    }

    public void getData(boolean withCleanCache, DataUpdater updater) {
        if (withCleanCache) {
            if (isConnected()) {
                jsonObject = null;
            } else {
                Dialog.displayToast(application, R.string.network_missing);
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
                if (jsonObject == null) {
                    jsonObject = new JSONObject()
                            .put(CURRENCIES, AssetServices.getRates(CURRENCIES))
                            .put(CRYPTOS, AssetServices.getRates(CRYPTOS))
                            .put(METALS, AssetServices.getRates(METALS));
                }
                return jsonObject;
            }

            @SneakyThrows
            @Override
            protected void onPostExecute(JSONObject result) {
                updater.update(result);
            }
        };
    }

    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
