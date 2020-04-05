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
import com.example.assets.util.ApiDataProvider;
import com.example.assets.util.StorageManager;
import com.example.assets.util.ValueCalculator;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import lombok.SneakyThrows;

public class MainActivity extends AbstractListActivity {

    private TextView totalValue;

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();

        totalValue = findViewById(R.id.total_value);


        ExtendedFloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddAssetListActivity.class);
            startActivity(intent);
        });

        totalValue.setOnClickListener(v -> {
            System.out.println("from button ...............................................................................................");
            new ApiDataProvider(this).populateTextViews(true, this::updateTextViews);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("onResume main ...............................................................................................");
        new ApiDataProvider(this).populateTextViews(false, this::updateTextViews);
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

    private void updateTextViews(JSONObject rates) throws JSONException {
        System.out.println("VALUE UPDATED @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        StorageManager manager = new StorageManager(this);
        String s = ValueCalculator.calculateTotal(manager.readFile(), rates);
        totalValue.setText(getString(R.string.total_value_text_view, s));
        updateList(rates);
    }


    private void updateList(JSONObject rates) throws JSONException {
        StorageManager storage = new StorageManager(this);
        JSONObject assets = storage.readFile();
        List<FragmentValues> values = new ArrayList<>();
        Iterator<String> keys = assets.keys();

        while (keys.hasNext()) {
            String asset = keys.next();

            float unitsF = Float.parseFloat(assets.getString(asset));
            float unitPriceF = 1/Float.parseFloat(rates.getString(asset));
            float valueF = unitsF * unitPriceF;

            String units = getString(R.string.float_no_decimal, unitsF);
            String unitPrice = getString(R.string.float_two_decimal, unitPriceF);
            String value = getString(R.string.value_value, valueF);

            values.add(new FragmentValues(asset, units, unitPrice, value, "Additional info"));
        }

        FragmentTemplate template = new FragmentTemplate(
                R.layout.fragment_asset_details,
                R.id.asset,
                R.id.units,
                R.id.unit_price,
                R.id.value,
                R.id.additional_info);

        setUpList(R.id.asset_list, template, values);
    }

}
