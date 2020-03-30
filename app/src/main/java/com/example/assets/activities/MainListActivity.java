package com.example.assets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.assets.R;
import com.example.assets.RecyclerViewManager;
import com.example.assets.fragments.FragmentTemplate;
import com.example.assets.fragments.FragmentValues;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.Objects;

public class MainListActivity extends AbstractListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();

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
                R.layout.asset_details_fragment,
                R.id.asset,
                R.id.unit_price,
                R.id.units,
                R.id.value,
                R.id.additional_info);

        setUpList(template, values);

        ExtendedFloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainListActivity.this, AddAssetListActivity.class);
                startActivity(intent);
            }
        });
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void clickItem(TextView tv) {
        //nothing
    }
}
