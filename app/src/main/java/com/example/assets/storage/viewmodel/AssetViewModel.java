package com.example.assets.storage.viewmodel;

import android.app.Application;
import android.renderscript.Allocation;
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
import com.example.assets.storage.room.BaseCurrency;
import com.example.assets.util.ApiDataProvider;
import com.example.assets.util.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.SneakyThrows;

import static com.example.assets.constants.AssetConstants.CURRENCIES;

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
        assetDetails = Transformations.map(trigger, pair -> getAssetDetails(pair.first, pair.second, pair.third));
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
        assetRepository.setBaseCurrency(baseCurrency);
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
        if (assets != null && rates != null) {
            if (baseCurrency == null) {
                baseCurrency = new BaseCurrency("USD", 1f);
                assetRepository.setBaseCurrency(baseCurrency);
            }
            float baseCurrencyRate = 1 / Utils.toFloat(rates.getJSONObject(CURRENCIES).getString(baseCurrency.getSymbol()));
            baseCurrency.setRate(baseCurrencyRate);
            assetRepository.setBaseCurrency(baseCurrency);
            for (Asset asset : assets) {
                float rate = Utils.toFloat(rates.getJSONObject(asset.getType()).getString(asset.getSymbol())) * baseCurrency.getRate();
                assetDetails.add(new AssetDetails(asset, rate, baseCurrency));
            }
        }
        assetDetails.sort(Comparator.comparingDouble(AssetDetails::getValue).reversed());
        return assetDetails;
    }

    public LiveData<BaseCurrency> getBaseCurrency() {
        return assetRepository.getBaseCurrency();
    }

    static class CustomLiveData extends MediatorLiveData<Pair> {
        CustomLiveData(LiveData<List<Asset>> assets, LiveData<JSONObject> apiData, LiveData<BaseCurrency> base) {
            addSource(assets, (f) -> setValue(Pair.setFirst(assets.getValue())));
            addSource(apiData, (s) -> setValue(Pair.setSecond(apiData.getValue())));
            addSource(base, (t) -> setValue(Pair.setThird(base.getValue())));
        }
    }

    static class Pair {
        static Pair pair;
        static List<Asset> first;
        static JSONObject second;
        static BaseCurrency third;

        static Pair setFirst(List<Asset> first) {
            Pair.first = first;
            return pair;
        }

        static Pair setSecond(JSONObject second) {
            Pair.second = second;
            return pair;
        }

        static Pair setThird(BaseCurrency third) {
            Pair.third = third;
            return pair;
        }
    }
}
