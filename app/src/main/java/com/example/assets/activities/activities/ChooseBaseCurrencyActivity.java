package com.example.assets.activities.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.assets.R;
import com.example.assets.activities.abstract_.AbstractListActivity;
import com.example.assets.constants.AssetConstants;
import com.example.assets.constants.Constants;
import com.example.assets.storage.room.Asset;
import com.example.assets.util.ApiDataProvider;
import com.example.assets.util.BaseCurrency;
import com.example.assets.util.Utils;

import static com.example.assets.util.BaseCurrency.setBaseCurrency;

public class ChooseBaseCurrencyActivity extends AbstractListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_generic);
        setUpSimpleList(AssetConstants.ALL_CURRENCIES);
    }

    @Override
    public void clickItem(View v, TextView tv) {
        String assetSymbol = tv.getText().toString();
        new ApiDataProvider(getApplication())
                .getData(false, AssetConstants.CURRENCIES,
                        dataFromApi -> setBaseCurrency(new BaseCurrency(assetSymbol, 1 / Utils.toFloat(dataFromApi.getString(assetSymbol)))));

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
