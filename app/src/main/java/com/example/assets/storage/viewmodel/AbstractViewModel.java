package com.example.assets.storage.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.assets.storage.repository.AssetRepository;

import org.json.JSONObject;

import java.util.Map;

public abstract class AbstractViewModel extends AndroidViewModel {

    protected AssetRepository assetRepository;
    protected MutableLiveData<Map<String, Float>> rates = new MutableLiveData<>();

    protected AbstractViewModel(@NonNull Application application) {
        super(application);
        assetRepository = new AssetRepository(application);
    }
}
