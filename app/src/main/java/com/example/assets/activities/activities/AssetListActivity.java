package com.example.assets.activities.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.assets.R;
import com.example.assets.activities.abstract_.AbstractListActivity;
import com.example.assets.constants.AssetConstants;
import com.example.assets.storage.room.entity.Asset;

import java.util.ArrayList;

import lombok.SneakyThrows;

public class AssetListActivity extends AbstractListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_generic);
        ArrayList<String> assets = getIntent().getStringArrayListExtra(AssetConstants.SPECIFIC_ASSETS);
        setUpSimpleList(assets);
    }

    @SneakyThrows
    @Override
    public void clickItem(View v, TextView tv) {
        String assetSymbol = tv.getText().toString();
        String assetType = getIntent().getStringExtra(AssetConstants.ASSET_TYPE);
        Intent intent = new Intent(this, AddAssetActivity.class);
        intent.putExtra(AssetConstants.EDITED_ASSET, new Asset(assetSymbol, assetType, -1, null));
        startActivity(intent);
    }
}
