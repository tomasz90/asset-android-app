package com.example.assets.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.assets.R;
import com.example.assets.storage.room.entity.Asset;
import com.example.assets.util.Constants;

import java.util.ArrayList;
import java.util.Objects;

public class AssetListActivity extends AbstractListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_generic);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.asset_list_activity_title);

        ArrayList<String> specificAssets = getIntent().getStringArrayListExtra(Constants.SPECIFIC_ASSETS);
        setUpSimpleList(specificAssets.toArray());
    }

    @Override
    public void clickItem(View v, TextView tv) {
        String assetSymbol = tv.getText().toString();
        String assetType = getIntent().getStringExtra(Constants.ASSET_TYPE);
        Intent intent = new Intent(this, AddAssetActivity.class);
        intent.putExtra(Constants.EDITED_ASSET, new Asset(assetSymbol, assetType, -1, null));
        startActivity(intent);
    }
}
