package com.example.assets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

public class AddAssetsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_assets);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.AddAssetsActivity_title);
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
}
