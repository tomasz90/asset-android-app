package com.example.assets.activities.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.assets.R;
import com.example.assets.activities.abstract_.AbstractListActivity;
import com.example.assets.constants.AssetConstants;
import com.example.assets.storage.room.BaseCurrency;
import com.example.assets.storage.viewmodel.AssetViewModel;
import com.example.assets.util.ApiDataProvider;
import com.example.assets.util.Utils;

import java.util.Objects;

import static com.example.assets.constants.AssetConstants.CURRENCIES;

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
        new AssetViewModel(getApplication()).setBaseCurrency(new BaseCurrency(baseCurrencySymbol));
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
