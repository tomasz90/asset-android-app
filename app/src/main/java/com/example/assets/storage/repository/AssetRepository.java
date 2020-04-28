package com.example.assets.storage.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.assets.storage.room.Asset;
import com.example.assets.storage.room.AssetDao;
import com.example.assets.storage.room.AssetDataBase;
import com.example.assets.storage.room.BaseCurrency;
import com.example.assets.storage.room.BaseCurrencyDao;

import java.util.List;

public class AssetRepository {

    private AssetDao assetDao;
    private LiveData<List<Asset>> allAssets;
    private BaseCurrencyDao baseCurrencyDao;
    private LiveData<BaseCurrency> baseCurrency;

    private static final String INSERT = "insert";
    private static final String UPDATE = "update";
    private static final String UPSERT = "upsert";
    private static final String DELETE = "delete";
    private static final String DELETE_ALL = "delete_all";

    public AssetRepository(Application application) {
        AssetDataBase dataBase = AssetDataBase.getInstance(application);
        assetDao = dataBase.assetDao();
        allAssets = assetDao.getAll();
        baseCurrencyDao = dataBase.baseCurrencyDao();
        baseCurrency = baseCurrencyDao.get();
    }

    public void setBaseCurrency(BaseCurrency baseCurrency) {
        getAsyncBaseCurrencyTask(baseCurrencyDao).execute(baseCurrency);
    }

    public LiveData<BaseCurrency> getBaseCurrency() {
        return baseCurrency;
    }

    public LiveData<List<Asset>> getAllAssets() {
        return allAssets;
    }

    public void insert(Asset asset) {
        getAsyncAssetTask(INSERT, assetDao).execute(asset);
    }

    public void upsert(Asset asset) {
        getAsyncAssetTask(UPSERT, assetDao).execute(asset);
    }

    public void update(Asset asset) {
        getAsyncAssetTask(UPDATE, assetDao).execute(asset);
    }

    public void delete(Asset asset) {
        getAsyncAssetTask(DELETE, assetDao).execute(asset);
    }

    public void deleteAll() {
        getAsyncAssetTask(DELETE_ALL, assetDao).execute();
    }

    private static AsyncTask<Asset, Void, Void> getAsyncAssetTask(String query, AssetDao assetDao) {
        return new AsyncTask<Asset, Void, Void>() {

            @Override
            protected Void doInBackground(Asset... assets) {
                switch (query) {
                    case INSERT:
                        assetDao.insert(assets[0]);
                        break;
                    case UPDATE:
                        assetDao.update(assets[0]);
                        break;
                    case UPSERT:
                        assetDao.upsert(assets[0]);
                        break;
                    case DELETE:
                        assetDao.delete(assets[0]);
                        break;
                    case DELETE_ALL:
                        assetDao.deleteAll();
                        break;
                }
                return null;
            }
        };
    }

    private static AsyncTask<BaseCurrency, Void, Void> getAsyncBaseCurrencyTask(BaseCurrencyDao baseCurrencyDao) {
        return new AsyncTask<BaseCurrency, Void, Void>() {

            @Override
            protected Void doInBackground(BaseCurrency... baseCurrencies) {
                baseCurrencyDao.update(baseCurrencies[0]);
                return null;
            }
        };
    }
}
