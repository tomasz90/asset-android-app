package com.example.assets.storage.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.assets.storage.repository.AssetRepository;
import com.example.assets.storage.room.Asset;

import java.util.List;

public class AssetViewModel extends AndroidViewModel {

    private AssetRepository assetRepository;
    private LiveData<List<Asset>> allAssets;

    public AssetViewModel(@NonNull Application application) {
        super(application);
        assetRepository = new AssetRepository(application);
        allAssets = assetRepository.getAll();
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

    public LiveData<List<Asset>> getAll() {
        return allAssets;
    }
}
