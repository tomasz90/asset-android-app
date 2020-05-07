package com.example.assets.storage.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.assets.storage.room.BaseCurrency;

public class ChooseBaseCurrencyViewModel extends AbstractViewModel {

    public ChooseBaseCurrencyViewModel(@NonNull Application application) {
        super(application);
    }

    public void updateBaseCurrency(BaseCurrency baseCurrency) {
        assetRepository.updateBaseCurrency(baseCurrency);
    }
}
