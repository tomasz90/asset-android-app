package com.example.assets.activities.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.assets.R;
import com.example.assets.activities.abstract_.AbstractListActivity;
import com.example.assets.asset_types.AssetConstants;
import com.example.assets.constants.Constants;

import java.util.Arrays;
import java.util.List;

import lombok.SneakyThrows;

public class AddCurrencyListActivity extends AbstractListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_generic);

        List<String> items = Arrays.asList(
                AssetConstants.Currency.EUR,
                AssetConstants.Currency.USD,
                AssetConstants.Currency.CHF,
                AssetConstants.Currency.GBP,
                AssetConstants.Currency.JPY,
                AssetConstants.Currency.PLN,
                AssetConstants.Currency.NOK,
                AssetConstants.Currency.DDK,
                AssetConstants.Currency.SEK);

        setUpSimpleList(items);
    }

    @SneakyThrows
    @Override
    public void clickItem(View v, TextView tv) {
        // TODO: 4/3/2020 implelemnt put extra intent
        String symbolAsset = tv.getText().toString();
        Intent intent = new Intent(this, AddAssetActivity.class);
        intent.putExtra(Constants.ASSET, symbolAsset);
        startActivity(intent);
    }
}
