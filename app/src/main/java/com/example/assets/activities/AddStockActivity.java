package com.example.assets.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assets.R;
import com.example.assets.RecyclerViewManager;
import com.example.assets.asset_types.CryptoCurrencyType;
import com.example.assets.asset_types.StockType;
import com.example.assets.fragments.FragmentTemplate;
import com.example.assets.fragments.FragmentValues;

public class AddStockActivity extends AppCompatActivity implements ActionOnClickItem{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_list);

        FragmentValues[] fragmentValues = {
                new FragmentValues(StockType.STOCK),
                new FragmentValues(StockType.STOCK),
                new FragmentValues(StockType.STOCK),
                new FragmentValues(StockType.STOCK),
                new FragmentValues(StockType.STOCK),
                new FragmentValues(StockType.STOCK),
                new FragmentValues(StockType.STOCK),
                new FragmentValues(StockType.STOCK),
                new FragmentValues(StockType.STOCK),
                new FragmentValues(StockType.STOCK)};

        RecyclerViewManager manager = new RecyclerViewManager(this);
        FragmentTemplate assetDetailsTemplate = new FragmentTemplate(R.layout.generic_fragment, R.id.generic_asset);
        manager.setRecyclerView(this, R.id.generic_list, assetDetailsTemplate, fragmentValues);
    }

    @Override
    public void clickItem(TextView tv) {
        //do nothing
    }
}
