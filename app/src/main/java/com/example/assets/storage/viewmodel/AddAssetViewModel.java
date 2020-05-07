package com.example.assets.storage.viewmodel;

import android.app.Application;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.assets.storage.room.entity.Asset;
import com.example.assets.storage.room.entity.BaseCurrency;
import com.example.assets.util.MultiLiveData;

import org.json.JSONObject;

public class AddAssetViewModel extends AbstractViewModel {

    private LiveData<Pair<JSONObject, BaseCurrency>> ratesAndBaseCurrency;

    public AddAssetViewModel(@NonNull Application application) {
        super(application);

        LiveData<BaseCurrency> baseCurrency = assetRepository.getBaseCurrency();
        ratesAndBaseCurrency = new MultiLiveData.Double<>(rates, baseCurrency);
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
}
