package com.example.assets.activities.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.assets.R;
import com.example.assets.activities.abstract_.AbstractListActivity;
import com.example.assets.asset_types.ActivityMap;
import com.example.assets.asset_types.AssetType;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AddAssetListActivity extends AbstractListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_generic);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.add_assets_activity_title);

        List<String> items = Arrays.asList(
                AssetType.CURRENCIES,
                AssetType.NOBLE_METALS,
                AssetType.CRYPTOCCURRENCIES,
                AssetType.STOCKS,
                AssetType.REAL_ESTATES,
                AssetType.ETFS);

        setUpSimpleList(items);
    }

    @Override
    public void clickItem(View v, TextView tv) {
        String asset = tv.getText().toString();
        // TODO: 3/30/2020 Remove this condition when other cards will be implemented:
        if (asset.equals(AssetType.CURRENCIES)) {
            Intent intent = new Intent(this, ActivityMap.getActivity(asset));
            this.startActivity(intent);
        }
    }
}
