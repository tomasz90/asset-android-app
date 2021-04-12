package com.example.assets.storage.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.assets.AppContainer;
import com.example.assets.api.DataProvider;
import com.example.assets.api.RateFacade;
import com.example.assets.storage.repository.AssetRepository;

import java.util.Map;

public abstract class AbstractViewModel extends AndroidViewModel {

    protected AssetRepository assetRepository;
    protected MutableLiveData<Map<String, Float>> rates = new MutableLiveData<>();
    protected Application application;

    protected AbstractViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        assetRepository = new AssetRepository(application);
        updateRates(false);
    }

    public void updateRates(boolean withCleanCache) {
        RateFacade rateFacade = AppContainer.Companion.getAppContainer().getRateFacade();
        new DataProvider(application, rateFacade).getData(withCleanCache, rates::setValue);
    }
}
