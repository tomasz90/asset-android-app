package com.example.assets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.assets.R;
import com.example.assets.activities.abstract_.AbstractListActivity;
import com.example.assets.asset_types.CurrencyType;
import com.example.assets.constants.IntentExtra;
import com.example.assets.fragments.FragmentTemplate;
import com.example.assets.fragments.FragmentValues;

import java.util.Arrays;
import java.util.List;

import lombok.SneakyThrows;

public class AddCurrencyListActivity extends AbstractListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_generic);

        List<FragmentValues> values = Arrays.asList(
                new FragmentValues(CurrencyType.EUR),
                new FragmentValues(CurrencyType.USD),
                new FragmentValues(CurrencyType.CHF),
                new FragmentValues(CurrencyType.GBP),
                new FragmentValues(CurrencyType.JPY),
                new FragmentValues(CurrencyType.PLN),
                new FragmentValues(CurrencyType.NOK),
                new FragmentValues(CurrencyType.DDK),
                new FragmentValues(CurrencyType.SEK));

        FragmentTemplate template = new FragmentTemplate(R.layout.fragment_generic, R.id.generic_asset);
        setUpList(R.id.generic_list, template, values);
    }

    @SneakyThrows
    @Override
    public void clickItem(View v, TextView tv) {
        // TODO: 4/3/2020 implelemnt put extra intent
        String symbolAsset = tv.getText().toString();
        Intent intent = new Intent(this, AddAssetActivity.class);
        intent.putExtra(IntentExtra.ASSET, symbolAsset);
        startActivity(intent);
    }
}
