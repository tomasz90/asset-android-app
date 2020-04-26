package com.example.assets.activities.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.assets.R;
import com.example.assets.activities.abstract_.AbstractListActivity;
import com.example.assets.constants.Constants;

import java.util.ArrayList;

import lombok.SneakyThrows;

public class AssetListActivity extends AbstractListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_generic);
        ArrayList<String> assets = getIntent().getStringArrayListExtra(Constants.SPECIFIC_ASSETS);
        setUpSimpleList(assets);
    }

    @SneakyThrows
    @Override
    public void clickItem(View v, TextView tv) {
        String symbolAsset = tv.getText().toString();
        Intent intent = new Intent(this, AddAssetActivity.class);
        intent.putExtra(Constants.ASSET_SYMBOL, symbolAsset);
        intent.putExtra(Constants.ASSET_TYPE, getIntent().getStringExtra(Constants.ASSET_TYPE));
        startActivity(intent);
    }
}
