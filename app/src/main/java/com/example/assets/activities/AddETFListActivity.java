package com.example.assets.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.example.assets.R;
import com.example.assets.RecyclerViewManager;
import com.example.assets.asset_types.ETFType;
import com.example.assets.fragments.FragmentTemplate;
import com.example.assets.fragments.FragmentValues;

public class AddETFListActivity extends AbstractListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_list);

        FragmentValues[] values = {
                new FragmentValues(ETFType.ETF),
                new FragmentValues(ETFType.ETF),
                new FragmentValues(ETFType.ETF),
                new FragmentValues(ETFType.ETF),
                new FragmentValues(ETFType.ETF),
                new FragmentValues(ETFType.ETF),
                new FragmentValues(ETFType.ETF),
                new FragmentValues(ETFType.ETF),
                new FragmentValues(ETFType.ETF)};

        FragmentTemplate template = new FragmentTemplate(R.layout.generic_fragment, R.id.generic_asset);
        setUpList(template, values);
    }

    @Override
    public void clickItem(TextView tv) {
        //do nothing
    }
}
