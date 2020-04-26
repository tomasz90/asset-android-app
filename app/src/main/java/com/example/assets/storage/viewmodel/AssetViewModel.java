package com.example.assets.storage.viewmodel;

import android.app.Application;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.assets.storage.repository.AssetRepository;
import com.example.assets.storage.room.Asset;
import com.example.assets.storage.room.AssetDetails;
import com.example.assets.util.ApiDataProvider;
import com.example.assets.util.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.SneakyThrows;

public class AssetViewModel extends AndroidViewModel {

    private AssetRepository assetRepository;
    private MutableLiveData<JSONObject> rates = new MutableLiveData();
    private LiveData<List<AssetDetails>> assetDetails;
    private Application application;

    public AssetViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        assetRepository = new AssetRepository(application);
        LiveData<List<Asset>> allAssets = assetRepository.getAll();
        refreshDataFromCache(false);
        CustomLiveData trigger = new CustomLiveData(allAssets, rates);
        assetDetails = Transformations.map(trigger, value -> getAssetDetails(value.first, value.second));
    }

    public void insert(Asset asset) {
        assetRepository.insert(asset);
    }

    public void insertOrUpdate(Asset asset) {
        assetRepository.insertOrUpdate(asset);
    }

    public void update(Asset asset) {
        assetRepository.update(asset);
    }

    public void delete(Asset asset) {
        assetRepository.delete(asset);
    }

    public void deleteAll() {
        assetRepository.deleteAll();
    }

    public LiveData<List<AssetDetails>> getAll() {
        return assetDetails;
    }

    @SneakyThrows
    public void refreshDataFromCache(boolean withCleanCache) {
        new ApiDataProvider(application).getData(withCleanCache, dataFromApi -> rates.setValue(dataFromApi));
    }

    @SneakyThrows
    private List<AssetDetails> getAssetDetails(List<Asset> assets, JSONObject rates) {
        List<AssetDetails> assetDetails = new ArrayList<>();
        if (assets != null && rates != null) {
            for (Asset asset : assets) {
                String rate = rates.getJSONObject(asset.getType()).getString(asset.getSymbol());
                assetDetails.add(new AssetDetails(asset, Utils.toFloat(rate)));
            }
        }
        assetDetails.sort(Comparator.comparingDouble(AssetDetails::getValue).reversed());
        return assetDetails;
    }

    static class CustomLiveData extends MediatorLiveData<Pair<List<Asset>, JSONObject>> {
        CustomLiveData(LiveData<List<Asset>> assets, LiveData<JSONObject> apiData) {
            addSource(assets, first -> setValue(Pair.create(first, apiData.getValue())));
            addSource(apiData, second -> setValue(Pair.create(assets.getValue(), second)));
        }
    }
}
