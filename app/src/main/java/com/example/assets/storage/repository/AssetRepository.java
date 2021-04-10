package com.example.assets.storage.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.assets.storage.room.AssetDataBase;
import com.example.assets.storage.room.dao.AssetDao;
import com.example.assets.storage.room.dao.BaseCurrencyDao;
import com.example.assets.storage.room.entity.Asset;
import com.example.assets.storage.room.entity.BaseCurrency;
import com.example.assets.util.ApiDataProvider;

import org.json.JSONObject;

import java.util.List;

import lombok.SneakyThrows;

public class AssetRepository {

    private AssetDao assetDao;
    private BaseCurrencyDao baseCurrencyDao;
    private LiveData<List<Asset>> allAssets;
    private LiveData<BaseCurrency> baseCurrency;

    private static final String INSERT = "insert";
    private static final String UPDATE = "update";
    private static final String UPSERT = "upsert";
    private static final String DELETE = "delete";
    private static final String DELETE_ALL = "delete_all";

    public AssetRepository(Application application) {
        AssetDataBase dataBase = AssetDataBase.getInstance(application);
        assetDao = dataBase.assetDao();
        baseCurrencyDao = dataBase.baseCurrencyDao();
        allAssets = assetDao.getAll();
        baseCurrency = baseCurrencyDao.get();
    }

    public LiveData<BaseCurrency> getBaseCurrency() {
        return baseCurrency;
    }

    public void updateBaseCurrency(BaseCurrency baseCurrency) {
        updateBaseCurrencyQuery(baseCurrencyDao).execute(baseCurrency);
    }

    public LiveData<List<Asset>> getAllAssets() {
        return allAssets;
    }

    public void upsertAsset(Asset asset) {
        asyncAssetQuery(UPSERT, assetDao).execute(asset);
    }

    public void updateAsset(Asset asset) {
        asyncAssetQuery(UPDATE, assetDao).execute(asset);
    }

    public void deleteAsset(Asset asset) {
        asyncAssetQuery(DELETE, assetDao).execute(asset);
    }

    public void deleteAllAsset() {
        asyncAssetQuery(DELETE_ALL, assetDao).execute();
    }

    private static AsyncTask<Asset, Void, Void> asyncAssetQuery(String query, AssetDao assetDao) {
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

    private static AsyncTask<BaseCurrency, Void, Void> updateBaseCurrencyQuery(BaseCurrencyDao baseCurrencyDao) {
        return new AsyncTask<BaseCurrency, Void, Void>() {

            @Override
            protected Void doInBackground(BaseCurrency... baseCurrencies) {
                baseCurrencyDao.update(baseCurrencies[0]);
                return null;
            }
        };
    }
}
