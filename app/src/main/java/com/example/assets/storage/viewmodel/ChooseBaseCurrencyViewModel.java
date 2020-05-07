package com.example.assets.storage.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.assets.storage.repository.AssetRepository;
import com.example.assets.storage.room.BaseCurrency;

public class ChooseBaseCurrencyViewModel extends AndroidViewModel {

    private AssetRepository assetRepository;
    public ChooseBaseCurrencyViewModel(@NonNull Application application) {
        super(application);
        assetRepository = new AssetRepository(application);
    }

    public void updateBaseCurrency(BaseCurrency baseCurrency) {
        assetRepository.updateBaseCurrency(baseCurrency);
    }
}
