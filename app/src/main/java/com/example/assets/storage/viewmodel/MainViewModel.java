package com.example.assets.storage.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.assets.storage.room.entity.Asset;
import com.example.assets.storage.room.entity.AssetDetails;
import com.example.assets.storage.room.entity.BaseCurrency;
import com.example.assets.util.ApiDataProvider;
import com.example.assets.util.customlivedata.MultiLiveData.Quadruple;
import com.example.assets.util.customlivedata.Quadruplet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import lombok.SneakyThrows;

import static androidx.lifecycle.Transformations.map;

public class MainViewModel extends AbstractViewModel {

    private LiveData<List<AssetDetails>> assetDetails;
    private MutableLiveData<Boolean> refreshTrigger = new MutableLiveData<>();
    private Application application;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        updateRates(false);

        LiveData<Quadruplet<List<Asset>, BaseCurrency, Map<String, Float>, Boolean>> quadrupleLiveData =
                new Quadruple<>(assetRepository.getAllAssets(), assetRepository.getBaseCurrency(), rates, refreshTrigger);
        assetDetails = map(quadrupleLiveData, quadruplet -> createAssetDetails(quadruplet.first, quadruplet.second, quadruplet.third));
    }

    public void deleteAsset(Asset asset) {
        assetRepository.deleteAsset(asset);
    }

    public void deleteAllAsset() {
        assetRepository.deleteAllAsset();
    }

    public LiveData<List<AssetDetails>> getAssetDetails() {
        return assetDetails;
    }

    public void updateRates(boolean withCleanCache) {
        new ApiDataProvider(application).getData(withCleanCache, rates::setValue);
    }

    public void refresh() {
        refreshTrigger.setValue(true);
    }

    @SneakyThrows
    private List<AssetDetails> createAssetDetails(List<Asset> assets, BaseCurrency baseCurrency, Map<String, Float> rates) {
        List<AssetDetails> assetDetails = new ArrayList<>();
        if (assets != null && rates != null && baseCurrency != null) {
            for (Asset asset : assets) {
                float baseCurrencyRate = 1 / rates.get(baseCurrency.getSymbol());
                float rate = rates.get(asset.getSymbol()) * baseCurrencyRate;
                assetDetails.add(new AssetDetails(asset, baseCurrency, rate));
            }
            assetDetails.sort(Comparator.comparingDouble(AssetDetails::getValue).reversed());
            return assetDetails;
        }
        return null;
    }
}
