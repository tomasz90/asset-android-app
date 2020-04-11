package com.example.assets.activities.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.assets.R;
import com.example.assets.activities.abstract_.AbstractListActivity;
import com.example.assets.asset_types.AssetConstants;
import com.example.assets.constants.IntentExtra;

import java.util.Arrays;
import java.util.List;

import lombok.SneakyThrows;

public class AddCurrencyListActivity extends AbstractListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_generic);

        List<String> items = Arrays.asList(
                AssetConstants.CurrencyType.EUR,
                AssetConstants.CurrencyType.USD,
                AssetConstants.CurrencyType.CHF,
                AssetConstants.CurrencyType.GBP,
                AssetConstants.CurrencyType.JPY,
                AssetConstants.CurrencyType.PLN,
                AssetConstants.CurrencyType.NOK,
                AssetConstants.CurrencyType.DDK,
                AssetConstants.CurrencyType.SEK);

        setUpSimpleList(items);
    }

    @SneakyThrows
    @Override
    public void clickItem(View v, TextView tv) {
        // TODO: 4/3/2020 implelemnt put extra intent
        String symbolAsset = tv.getText().toString();
        Intent intent = new Intent(this, AddAssetActivity.class);
        intent.putExtra(IntentExtra.ASSET, symbolAsset);
        startActivity(intent);
    }
}
