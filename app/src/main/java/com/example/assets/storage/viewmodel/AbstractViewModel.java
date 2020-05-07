package com.example.assets.storage.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.assets.storage.repository.AssetRepository;
import com.example.assets.util.ApiDataProvider;

import org.json.JSONObject;

import lombok.SneakyThrows;

public abstract class AbstractViewModel extends AndroidViewModel {

    protected Application application;
    protected AssetRepository assetRepository;

    protected MutableLiveData<JSONObject> rates = new MutableLiveData();

    protected AbstractViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        assetRepository = new AssetRepository(application);
        refreshDataFromCache(false);
    }

    @SneakyThrows
    public void refreshDataFromCache(boolean withCleanCache) {
        new ApiDataProvider(application).getData(withCleanCache, dataFromApi -> rates.setValue(dataFromApi));
    }
}
