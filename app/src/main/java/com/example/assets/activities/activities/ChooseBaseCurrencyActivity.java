package com.example.assets.activities.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.assets.R;
import com.example.assets.activities.abstract_.AbstractListActivity;
import com.example.assets.constants.AssetConstants;
import com.example.assets.storage.room.BaseCurrency;
import com.example.assets.storage.viewmodel.ChooseBaseCurrencyViewModel;
import com.example.assets.storage.viewmodel.MainViewModel;

import java.util.Objects;

public class ChooseBaseCurrencyActivity extends AbstractListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_generic);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.choose_base_currency);
        setUpSimpleList(AssetConstants.ALL_CURRENCIES);
    }

    @Override
    public void clickItem(View v, TextView tv) {
        String baseCurrencySymbol = tv.getText().toString();
        new ChooseBaseCurrencyViewModel(getApplication()).updateBaseCurrency(new BaseCurrency(baseCurrencySymbol));
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
