package com.example.assets.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.example.assets.R;
import com.example.assets.RecyclerViewManager;
import com.example.assets.asset_types.StockType;
import com.example.assets.fragments.FragmentTemplate;
import com.example.assets.fragments.FragmentValues;

public class AddStockListActivity extends AbstractListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_list);

        FragmentValues[] values = {
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

        FragmentTemplate template = new FragmentTemplate(R.layout.generic_fragment, R.id.generic_asset);
        setUpList(R.id.generic_list, template, values);
    }

    @Override
    public void clickItem(TextView tv) {
        //do nothing
    }
}
