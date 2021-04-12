package com.example.assets.api;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.example.assets.R;
import com.example.assets.util.Dialog;

import java.util.HashMap;
import java.util.Map;

public class DataProvider {

    public DataProvider(Application application, RateFacade rateFacade) {
        this.application = application;
        this.rateFacade = rateFacade;
    }

    private final Application application;
    private final RateFacade rateFacade;
    private static Map<String, Float> rates = new HashMap<>();

    public interface DataUpdater {
        void update(Map<String, Float> apiRates);
    }

    public void getData(boolean withCleanCache, DataUpdater updater) {
        if (withCleanCache) {
            if (isConnected()) {
                rates.clear();
            } else {
                Dialog.displayToast(application, R.string.network_missing);
                return;
            }
        }
        getAsync(updater, rateFacade).execute();
    }

    private static AsyncTask<String, Void, Map<String, Float>> getAsync(
            DataUpdater updater, RateFacade rateFacade) {
        return new AsyncTask<String, Void, Map<String, Float>>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Map<String, Float> doInBackground(String... strings) {
                if (rates.isEmpty()) {
                    rates = rateFacade.getRates();
                }
                return rates;
            }

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
