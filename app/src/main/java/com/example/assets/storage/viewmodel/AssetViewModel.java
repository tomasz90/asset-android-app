package com.example.assets.storage.viewmodel;

import android.app.Application;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;


import com.example.assets.storage.repository.AssetRepository;
import com.example.assets.storage.room.Asset;
import com.example.assets.util.ApiDataProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import lombok.SneakyThrows;

public class AssetViewModel extends AndroidViewModel {

    private AssetRepository assetRepository;
    private LiveData<List<Asset>> allAssets;
    private MutableLiveData mutableLiveData = new MutableLiveData();
    private CustomLiveData customLiveData;

    public AssetViewModel(@NonNull Application application) {
        super(application);
        assetRepository = new AssetRepository(application);
        allAssets = assetRepository.getAll();
        setMutableLiveDataFromApi();
        customLiveData = new CustomLiveData(allAssets, mutableLiveData);
    }

    public void insert(Asset asset) {
        assetRepository.insert(asset);
    }

    public void update(Asset asset) {
        assetRepository.insert(asset);
    }

    public void delete(Asset asset) {
        assetRepository.delete(asset);
    }

    public void deleteAll() {
       assetRepository.deleteAll();
    }

    @SneakyThrows
    public void setMutableLiveDataFromApi() {
        new ApiDataProvider().populateTextViews(true, new ApiDataProvider.DataUpdater() {
            @Override
            public void updateUI(JSONObject dataFromApi) throws JSONException {
                mutableLiveData.setValue(dataFromApi);
            }
        });
    }

    public MediatorLiveData<Pair<List<Asset>, JSONObject>> getAll() {
        return customLiveData;
    }


    class CustomLiveData extends MediatorLiveData<Pair<List<Asset>, JSONObject>> {
        public CustomLiveData(LiveData<List<Asset>> code, LiveData<JSONObject> nbDays) {
            addSource(code, new Observer<List<Asset>>() {
                public void onChanged(@Nullable List<Asset> first) {
                    setValue(Pair.create(first, nbDays.getValue()));
                }
            });
            addSource(nbDays, new Observer<JSONObject>() {
                public void onChanged(@Nullable JSONObject second) {
                    setValue(Pair.create(code.getValue(), second));
                }
            });
        }
    }
}
