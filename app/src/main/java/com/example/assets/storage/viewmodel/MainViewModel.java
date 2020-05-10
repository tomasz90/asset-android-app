package com.example.assets.storage.viewmodel;

import android.app.Application;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.assets.storage.room.entity.Asset;
import com.example.assets.storage.room.entity.AssetDetails;
import com.example.assets.storage.room.entity.BaseCurrency;
import com.example.assets.util.customlivedata.MultiLiveData;
import com.example.assets.util.customlivedata.Quadruplet;
import com.example.assets.util.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.SneakyThrows;

import static com.example.assets.constants.AssetConstants.CURRENCIES;

public class MainViewModel extends AbstractViewModel {

    private LiveData<Pair<List<AssetDetails>, BaseCurrency>> assetDetails;
    private MutableLiveData<Boolean> refreshTrigger = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);

        LiveData<BaseCurrency> baseCurrency = assetRepository.getBaseCurrency();
        LiveData<List<Asset>> allAssets = assetRepository.getAllAssets();

        LiveData<Quadruplet<List<Asset>, JSONObject, BaseCurrency, Boolean>> tripleLiveData = new MultiLiveData.Quadruple<>(allAssets, rates, baseCurrency, refreshTrigger);
        assetDetails = Transformations.map(tripleLiveData, triplet -> createAssetDetails(triplet.first, triplet.second, triplet.third));
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

    public void updateRates(boolean withCleanCache) {
        assetRepository.updateRates(withCleanCache);
    }

    public void refresh() {
        refreshTrigger.setValue(true);
    }
}
