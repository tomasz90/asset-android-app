package com.example.assets.storage.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.assets.storage.repository.AssetRepository;
import com.example.assets.storage.room.Asset;
import com.example.assets.storage.room.AssetDetails;
import com.example.assets.storage.room.BaseCurrency;
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
        LiveData<List<Asset>> allAssets = assetRepository.getAllAssets();
        LiveData<BaseCurrency> baseCurrency = assetRepository.getBaseCurrency();
        refreshDataFromCache(false);
        CustomLiveData trigger = new CustomLiveData(allAssets, rates, baseCurrency);
        assetDetails = Transformations.map(trigger, triplet -> getAssetDetails(triplet.first, triplet.second, triplet.third));
    }

    public void insert(Asset asset) {
        assetRepository.insert(asset);
    }

    public void insertOrUpdate(Asset asset) {
        assetRepository.upsert(asset);
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

    public void setBaseCurrency(BaseCurrency baseCurrency) {
        assetRepository.updateBaseCurrency(baseCurrency);
    }

    public LiveData<List<AssetDetails>> getAll() {
        return assetDetails;
    }

    @SneakyThrows
    public void refreshDataFromCache(boolean withCleanCache) {
        new ApiDataProvider(application).getData(withCleanCache, dataFromApi -> rates.setValue(dataFromApi));
    }

    @SneakyThrows
    private List<AssetDetails> getAssetDetails(List<Asset> assets, JSONObject rates, BaseCurrency baseCurrency) {
        List<AssetDetails> assetDetails = new ArrayList<>();
        if (assets != null && rates != null && baseCurrency != null) {
            for (Asset asset : assets) {
                float rate = Utils.toFloat(rates.getJSONObject(asset.getType()).getString(asset.getSymbol())) * baseCurrency.getRate();
                assetDetails.add(new AssetDetails(asset, rate, baseCurrency));
            }
            assetDetails.sort(Comparator.comparingDouble(AssetDetails::getValue).reversed());
            return assetDetails;
        }
        return null;
    }

    public LiveData<BaseCurrency> getBaseCurrency() {
        return assetRepository.getBaseCurrency();
    }

    class CustomLiveData extends MediatorLiveData<Triplet> {
        CustomLiveData(LiveData<List<Asset>> assets, LiveData<JSONObject> apiData, LiveData<BaseCurrency> base) {
            addSource(assets, assets1 -> setValue(Triplet.create(assets1, apiData.getValue(), base.getValue())));
            addSource(apiData, apiData1 -> setValue(Triplet.create(assets.getValue(), apiData1, base.getValue())));
            addSource(base, base1 -> setValue(Triplet.create(assets.getValue(), apiData.getValue(), base1)));
        }
    }

    static class Triplet {
        List<Asset> first;
        JSONObject second;
        BaseCurrency third;

        private Triplet(List<Asset> first, JSONObject second, BaseCurrency third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }

        static Triplet create(List<Asset> first, JSONObject second, BaseCurrency third) {
            return new Triplet(first, second, third);
        }
    }
}
