package com.example.assets.util;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.example.assets.R;

import org.json.JSONObject;

import java.util.Map;

import lombok.SneakyThrows;

import static com.example.assets.constants.AssetConstants.CRYPTOS;
import static com.example.assets.constants.AssetConstants.CURRENCIES;
import static com.example.assets.constants.AssetConstants.METALS;

public class ApiDataProvider {

    private final Application application;
    private static Map<String, Float> rates;

    public ApiDataProvider(Application application) {
        this.application = application;
    }

    public interface DataUpdater {
        void update(Map<String, Float> apiRates);
    }

    public void getData(boolean withCleanCache, DataUpdater updater) {
        if (withCleanCache) {
            if (isConnected()) {
                rates = null;
            } else {
                Dialog.displayToast(application, R.string.network_missing);
                return;
            }
        }
        getAsync(updater).execute();
    }

    private static AsyncTask<String, Void, Map<String, Float>> getAsync(DataUpdater updater) {
        return new AsyncTask<String, Void, Map<String, Float>>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @SneakyThrows
            @Override
            protected Map<String, Float> doInBackground(String... strings) {
                if (rates == null) {
                    rates = new RateFacade().getRates();
                }
                return rates;
            }

            @SneakyThrows
            @Override
            protected void onPostExecute(Map<String, Float> result) {
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
