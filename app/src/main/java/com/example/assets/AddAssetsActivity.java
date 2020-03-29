package com.example.assets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.assets.fragments.AssetType;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AddAssetsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_assets);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.add_assets_activity_title);
    }

    public void onClickNobleButton(View view) {
        Intent intent = new Intent(AddAssetsActivity.this, AddNobleMetalActivity.class);
        startActivity(intent);
    }

    public void onClickCryptoButton(View view) {
        onClickNobleButton(view);
    }

    public void onClickStocksButton(View view) {
        onClickNobleButton(view);
    }

    private List<String> getAssetTypes() {
        return Arrays.asList(AssetType.NOBLE_METAL, AssetType.CRYPTOCCURRENCIES, AssetType.STOCKS);
    }
}
