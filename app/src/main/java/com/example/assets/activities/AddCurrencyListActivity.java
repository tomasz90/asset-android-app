package com.example.assets.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.example.assets.R;
import com.example.assets.RecyclerViewManager;
import com.example.assets.asset_types.CurrencyType;
import com.example.assets.fragments.FragmentTemplate;
import com.example.assets.fragments.FragmentValues;

public class AddCurrencyListActivity extends AbstractListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_list);

        FragmentValues[] values = {
                new FragmentValues(CurrencyType.EUR),
                new FragmentValues(CurrencyType.USD),
                new FragmentValues(CurrencyType.CHF),
                new FragmentValues(CurrencyType.GBP),
                new FragmentValues(CurrencyType.JPY),
                new FragmentValues(CurrencyType.PLN),
                new FragmentValues(CurrencyType.NOK),
                new FragmentValues(CurrencyType.DDK),
                new FragmentValues(CurrencyType.SEK)};

        FragmentTemplate template = new FragmentTemplate(R.layout.generic_fragment, R.id.generic_asset);
        setUpList(R.id.generic_list, template, values);
    }

    @Override
    public void clickItem(TextView tv) {
        //do nothing
    }
}
