package com.example.assets.storage.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.assets.storage.room.entity.Asset;
import com.example.assets.storage.room.entity.AssetDetails;
import com.example.assets.storage.room.entity.BaseCurrency;
import com.example.assets.util.custom_livedata.MultiLiveData.Quadruple;
import com.example.assets.util.custom_livedata.Quadruplet;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static androidx.lifecycle.Transformations.map;
import static java.util.Collections.emptyList;
import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.toList;

public class MainViewModel extends AbstractViewModel {

    private LiveData<List<AssetDetails>> assetDetails;
    private MutableLiveData<Boolean> refreshTrigger = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);

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

    public void refresh() {
        refreshTrigger.setValue(true);
    }

    private List<AssetDetails> createAssetDetails(List<Asset> assets, BaseCurrency baseCurrency, Map<String, Float> rates) {
        if (isAnyObjectNull(assets, rates, baseCurrency)) {
            return emptyList();
        }
        return assets.stream()
                .map(asset -> {
                    float baseCurrencyRate = 1 / rates.get(baseCurrency.getSymbol());
                    float rate = rates.get(asset.getSymbol()) * baseCurrencyRate;
                    return new AssetDetails(asset, baseCurrency, rate);
                })
                .sorted(comparingDouble(AssetDetails::getValue).reversed())
                .collect(toList());
    }

    boolean isAnyObjectNull(Object... objects) {
        return Arrays.stream(objects).anyMatch(Objects::isNull);
    }
}
