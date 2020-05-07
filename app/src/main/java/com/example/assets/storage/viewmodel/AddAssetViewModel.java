package com.example.assets.storage.viewmodel;

import android.app.Application;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.assets.storage.repository.AssetRepository;
import com.example.assets.storage.room.Asset;
import com.example.assets.storage.room.BaseCurrency;
import com.example.assets.util.ApiDataProvider;

import org.json.JSONObject;

import lombok.SneakyThrows;

public class AddAssetViewModel extends AndroidViewModel {

    private AssetRepository assetRepository;
    private MutableLiveData<JSONObject> rates = new MutableLiveData();
    private RatesAndBaseCurrencyLiveData ratesAndBaseCurrency;

    private Application application;

    public AddAssetViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        assetRepository = new AssetRepository(application);

        refreshDataFromCache(false);

        LiveData<BaseCurrency> baseCurrency = assetRepository.getBaseCurrency();
        ratesAndBaseCurrency = new RatesAndBaseCurrencyLiveData(rates, baseCurrency);
    }

    @SneakyThrows
    public void refreshDataFromCache(boolean withCleanCache) {
        new ApiDataProvider(application).getData(withCleanCache, dataFromApi -> rates.setValue(dataFromApi));
    }

    public void upsertAsset(Asset asset) {
        assetRepository.upsertAsset(asset);
    }

    public void updateAsset(Asset asset) {
        assetRepository.updateAsset(asset);
    }

    public LiveData<Pair<JSONObject, BaseCurrency>> getRatesAndBaseCurrency() {
        return ratesAndBaseCurrency;
    }

    static class RatesAndBaseCurrencyLiveData extends MediatorLiveData<Pair<JSONObject, BaseCurrency>> {
        RatesAndBaseCurrencyLiveData(LiveData<JSONObject> apiData, LiveData<BaseCurrency> base) {
            addSource(apiData, apiData1 -> setValue(Pair.create(apiData1, base.getValue())));
            addSource(base, base1 -> setValue(Pair.create(apiData.getValue(), base1)));
        }
    }
}
