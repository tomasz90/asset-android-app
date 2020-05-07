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

import static com.example.assets.constants.AssetConstants.CURRENCIES;

public class MainViewModel extends AndroidViewModel {

    private AssetRepository assetRepository;
    private MutableLiveData<JSONObject> rates = new MutableLiveData();
    private LiveData<List<AssetDetails>> assetDetails;

    private Application application;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        assetRepository = new AssetRepository(application);

        refreshDataFromCache(false);

        LiveData<BaseCurrency> baseCurrency = assetRepository.getBaseCurrency();
        LiveData<List<Asset>> allAssets = assetRepository.getAllAssets();

        AssetDetailsTrigger trigger = new AssetDetailsTrigger(allAssets, rates, baseCurrency);
        assetDetails = Transformations.map(trigger, triplet -> createAssetDetails(triplet.first, triplet.second, triplet.third));
    }

    @SneakyThrows
    public void refreshDataFromCache(boolean withCleanCache) {
        new ApiDataProvider(application).getData(withCleanCache, dataFromApi -> rates.setValue(dataFromApi));
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

    @SneakyThrows
    private List<AssetDetails> createAssetDetails(List<Asset> assets, JSONObject rates, BaseCurrency baseCurrency) {
        List<AssetDetails> assetDetails = new ArrayList<>();
        if (assets != null && rates != null && baseCurrency != null) {
            for (Asset asset : assets) {
                float baseCurrencyRate = 1 / Utils.toFloat(rates.getJSONObject(CURRENCIES).getString(baseCurrency.getSymbol()));
                float rate = Utils.toFloat(rates.getJSONObject(asset.getType()).getString(asset.getSymbol())) * baseCurrencyRate;
                assetDetails.add(new AssetDetails(asset, rate, baseCurrency));
            }
            assetDetails.sort(Comparator.comparingDouble(AssetDetails::getValue).reversed());
            return assetDetails;
        }
        return null;
    }

    static class AssetDetailsTrigger extends MediatorLiveData<Triplet> {
        AssetDetailsTrigger(LiveData<List<Asset>> assets, LiveData<JSONObject> apiData, LiveData<BaseCurrency> base) {
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
