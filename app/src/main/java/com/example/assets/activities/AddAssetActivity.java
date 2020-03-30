package com.example.assets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assets.R;
import com.example.assets.RecyclerViewManager;
import com.example.assets.asset_types.AssetType;
import com.example.assets.fragments.FragmentTemplate;
import com.example.assets.fragments.FragmentValues;

import java.util.Objects;

public class AddAssetActivity extends AppCompatActivity implements ActionOnClickItem {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_list);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.add_assets_activity_title);

        FragmentValues[] fragmentValues = {
                new FragmentValues(AssetType.NOBLE_METALS),
                new FragmentValues(AssetType.CURRENCIES),
                new FragmentValues(AssetType.CRYPTOCCURRENCIES),
                new FragmentValues(AssetType.STOCKS),
                new FragmentValues(AssetType.REAL_ESTATES),
                new FragmentValues(AssetType.ETFS)};

        RecyclerViewManager manager = new RecyclerViewManager(this);
        FragmentTemplate assetDetailsTemplate =
                new FragmentTemplate(R.layout.generic_fragment, R.id.generic_asset);
        manager.setRecyclerView(this, R.id.generic_list, assetDetailsTemplate, fragmentValues);
    }

    @Override
    public void clickItem(TextView tv) {
        String asset = tv.getText().toString();
        Intent intent = new Intent(this, ActivityMap.getActivity(asset));
        this.startActivity(intent);
    }
}
