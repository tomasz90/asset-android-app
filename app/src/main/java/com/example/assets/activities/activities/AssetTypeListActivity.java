package com.example.assets.activities.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.assets.R;
import com.example.assets.util.Constants;

import java.util.Objects;

public class AssetTypeListActivity extends AbstractListActivity {

    public enum AssetTypes { CURRENCIES, CRYPTOS, METALS }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_generic);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.asset_type_list_activity_title);

        setUpSimpleList(AssetTypes.values());
    }

    @Override
    public void clickItem(View v, TextView tv) {
        String assetType = tv.getText().toString();
        Intent intent = new Intent(this, AssetListActivity.class);
        intent.putStringArrayListExtra(Constants.SPECIFIC_ASSETS, Constants.assetMap.get(assetType));
        intent.putExtra(Constants.ASSET_TYPE, assetType);
        this.startActivity(intent);
    }
}
