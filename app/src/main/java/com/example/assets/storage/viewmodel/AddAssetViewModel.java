package com.example.assets.storage.viewmodel;

import android.app.Application;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.assets.storage.room.entity.Asset;
import com.example.assets.storage.room.entity.BaseCurrency;
import com.example.assets.util.customlivedata.MultiLiveData;

import org.json.JSONObject;

import java.util.Map;

public class AddAssetViewModel extends AbstractViewModel {

    private LiveData<Pair<Map<String, Float>, BaseCurrency>> ratesAndBaseCurrency;

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

    public LiveData<Pair<Map<String, Float>, BaseCurrency>> getRatesAndBaseCurrency() {
        return ratesAndBaseCurrency;
    }
}
