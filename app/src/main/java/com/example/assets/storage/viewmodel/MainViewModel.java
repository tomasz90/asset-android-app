package com.example.assets.storage.viewmodel;

import android.app.Application;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;

import com.example.assets.storage.room.entity.Asset;
import com.example.assets.storage.room.entity.AssetDetails;
import com.example.assets.storage.room.entity.BaseCurrency;
import com.example.assets.util.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.SneakyThrows;

import static com.example.assets.constants.AssetConstants.CURRENCIES;

public class MainViewModel extends AbstractViewModel {

    private LiveData<Pair<List<AssetDetails>, BaseCurrency>> assetDetails;

    public MainViewModel(@NonNull Application application) {
        super(application);

        LiveData<BaseCurrency> baseCurrency = assetRepository.getBaseCurrency();
        LiveData<List<Asset>> allAssets = assetRepository.getAllAssets();

        AssetDetailsTrigger trigger = new AssetDetailsTrigger(allAssets, rates, baseCurrency);
        assetDetails = Transformations.map(trigger, triplet -> createAssetDetails(triplet.first, triplet.second, triplet.third));
    }

    public void deleteAsset(Asset asset) {
        assetRepository.deleteAsset(asset);
    }

    public void deleteAllAsset() {
        assetRepository.deleteAllAsset();
    }

    public LiveData<Pair<List<AssetDetails>, BaseCurrency>> getAssetDetails() {
        return assetDetails;
    }

    @SneakyThrows
    private Pair<List<AssetDetails>, BaseCurrency> createAssetDetails(List<Asset> assets, JSONObject rates, BaseCurrency baseCurrency) {
        List<AssetDetails> assetDetails = new ArrayList<>();
        if (assets != null && rates != null && baseCurrency != null) {
            for (Asset asset : assets) {
                float baseCurrencyRate = 1 / Utils.toFloat(rates.getJSONObject(CURRENCIES).getString(baseCurrency.getSymbol()));
                float rate = Utils.toFloat(rates.getJSONObject(asset.getType()).getString(asset.getSymbol())) * baseCurrencyRate;
                assetDetails.add(new AssetDetails(asset, rate));
            }
            assetDetails.sort(Comparator.comparingDouble(AssetDetails::getValue).reversed());
            return new Pair<>(assetDetails, baseCurrency);
        }
        return null;
    }

    static class AssetDetailsTrigger extends MediatorLiveData<Triplet<List<Asset>, JSONObject, BaseCurrency>> {
        AssetDetailsTrigger(LiveData<List<Asset>> assets, LiveData<JSONObject> apiData, LiveData<BaseCurrency> base) {

            addSource(assets, _assets -> {
                JSONObject _apiData = apiData.getValue();
                BaseCurrency _base = base.getValue();
                if (isAllNotNull(_assets, _apiData, _base)) {
                    setValue(new Triplet<>(_assets, _apiData, _base));
                }
            });
            addSource(apiData, _apiData -> {
                List<Asset> _assets = assets.getValue();
                BaseCurrency _base = base.getValue();
                if (isAllNotNull(_assets, _apiData, _base)) {
                    setValue(new Triplet<>(_assets, _apiData, _base));
                }
            });
            addSource(base, _base -> {
                List<Asset> _assets = assets.getValue();
                JSONObject _apiData = apiData.getValue();
                if (isAllNotNull(_assets, _apiData, _base)) {
                    setValue(new Triplet<>(_assets, _apiData, _base));
                }
            });
        }
    }

    static class Triplet<S, T, U> {
        S first;
        T second;
        U third;

        private Triplet(S first, T second, U third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }
    }

    static boolean isAllNotNull(Object... objects) {
        for (Object o : objects) {
            if (o == null) {
                return false;
            }
        }
        return true;
    }
}
