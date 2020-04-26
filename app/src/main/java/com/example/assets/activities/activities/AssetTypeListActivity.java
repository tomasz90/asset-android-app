package com.example.assets.activities.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.assets.R;
import com.example.assets.activities.abstract_.AbstractListActivity;
import com.example.assets.constants.Constants;

import java.util.Objects;

public class AssetTypeListActivity extends AbstractListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_generic);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.add_assets_activity_title);

        setUpSimpleList(Constants.ALL_ASSETS);
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
