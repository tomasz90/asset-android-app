package com.example.assets.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.assets.R;
import com.example.assets.RecyclerViewManager;
import com.example.assets.asset_types.AssetType;
import com.example.assets.fragments.FragmentTemplate;
import com.example.assets.fragments.FragmentValues;

import java.util.Objects;

public class AddAssetsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_list);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.add_assets_activity_title);

        FragmentTemplate assetDetailsTemplate = new FragmentTemplate(R.layout.generic_fragment, R.id.asset_type_name);

        FragmentValues[] fragmentValues = {
        new FragmentValues(AssetType.NOBLE_METALS),
        new FragmentValues(AssetType.CURRENCIES),
        new FragmentValues(AssetType.CRYPTOCCURRENCIES),
        new FragmentValues(AssetType.STOCKS),
        new FragmentValues(AssetType.REAL_ESTATES),
        new FragmentValues(AssetType.ETFS)};

        RecyclerViewManager manager =  new RecyclerViewManager();
        manager.setRecyclerView(this, R.id.list, assetDetailsTemplate, fragmentValues);

    }

    public void onClickNobleButton(View view) {
        Intent intent = new Intent(AddAssetsActivity.this, AddNobleMetalsActivity.class);
        startActivity(intent);
    }

    public void onClickCryptoButton(View view) {
        onClickNobleButton(view);
    }

    public void onClickStocksButton(View view) {
        onClickNobleButton(view);
    }
}
