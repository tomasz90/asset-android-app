package com.example.assets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.assets.R;
import com.example.assets.activities.abstract_.AbstractListActivity;
import com.example.assets.fragments.FragmentTemplate;
import com.example.assets.fragments.FragmentValues;
import com.example.assets.util.DataProvider;
import com.example.assets.util.StorageManager;
import com.example.assets.util.ValueCalculator;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.json.JSONObject;

import java.util.Objects;

public class MainActivity extends AbstractListActivity {

    private TextView totalValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();

        totalValue = findViewById(R.id.total_value);


        FragmentValues[] values = {
                new FragmentValues("Platinum", "3oz", "1200 USD", "3600 USD", "Additional info"),
                new FragmentValues("Gold", "2oz", "1540 USD ", "3080 USD", "Additional info"),
                new FragmentValues("EUR", "4500", "1.12 USD", "5400 USD", "Additional info"),
                new FragmentValues("PLN", "53500", "0.24 USD", "12,840 USD", "Additional info"),
                new FragmentValues("ETH", "20", "151 USD", "3020 USD", "Additional info"),
                new FragmentValues("BTC", "0.1", "6100 USD", "610 USD", "Additional info"),
                new FragmentValues("LTC", "11", "24 USD", "264 USD", "Additional info"),
                new FragmentValues("PZU", "200", "15 USD", "3000 USD", "Additional info"),
                new FragmentValues("", "", "", "", "")};

        FragmentTemplate template = new FragmentTemplate(
                R.layout.fragment_asset_details,
                R.id.asset,
                R.id.units,
                R.id.unit_price,
                R.id.value,
                R.id.additional_info);

        setUpList(R.id.asset_list, template, values);

        ExtendedFloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddAssetListActivity.class);
            startActivity(intent);
        });

        totalValue.setOnClickListener(v -> {
            System.out.println("from button ...............................................................................................");
            DataProvider.execute(true, this::updateTotalValue);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("onResume main ...............................................................................................");
        DataProvider.execute(false, this::updateTotalValue);
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.main_activity_title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void clickItem(View v, TextView tv) {
        //this is for clicking item on the list
    }

    private void updateTotalValue(JSONObject object) {
        System.out.println("VALUE UPDATED @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        StorageManager manager = new StorageManager(this);
        String s = ValueCalculator.calculateTotal(manager.readFile(), object);
        totalValue.setText(getString(R.string.total_value_text_view, s));
    }
}
