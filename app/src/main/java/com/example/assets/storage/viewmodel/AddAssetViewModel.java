package com.example.assets.storage.viewmodel;

import android.app.Application;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.assets.storage.room.entity.Asset;
import com.example.assets.storage.room.entity.BaseCurrency;

import org.json.JSONObject;

public class AddAssetViewModel extends AbstractViewModel {

    private RatesAndBaseCurrencyLiveData ratesAndBaseCurrency;

    public AddAssetViewModel(@NonNull Application application) {
        super(application);

        LiveData<BaseCurrency> baseCurrency = assetRepository.getBaseCurrency();
        ratesAndBaseCurrency = new RatesAndBaseCurrencyLiveData(rates, baseCurrency);
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
