package com.example.assets.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assets.R;
import com.example.assets.RecyclerViewManager;
import com.example.assets.asset_types.CryptoCurrencyType;
import com.example.assets.fragments.FragmentTemplate;
import com.example.assets.fragments.FragmentValues;

public class AddCryptocurrencyActivity extends AppCompatActivity implements ActionOnClickItem{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_list);

        FragmentValues[] fragmentValues = {
                new FragmentValues(CryptoCurrencyType.BTC),
                new FragmentValues(CryptoCurrencyType.ETH),
                new FragmentValues(CryptoCurrencyType.LTC),
                new FragmentValues(CryptoCurrencyType.NEO),
                new FragmentValues(CryptoCurrencyType.IOTA),
                new FragmentValues(CryptoCurrencyType.XRP)};

                RecyclerViewManager manager = new RecyclerViewManager(this);
        FragmentTemplate assetDetailsTemplate = new FragmentTemplate(R.layout.generic_fragment, R.id.generic_asset);
        manager.setRecyclerView(this, R.id.generic_list, assetDetailsTemplate, fragmentValues);
    }

    @Override
    public void perform(TextView tv) {
        //do nothing
    }
}
