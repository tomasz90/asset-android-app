package com.example.assets.activities.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.assets.R;
import com.example.assets.activities.abstract_.AbstractListActivity;
import com.example.assets.storage.repository.AssetRepository;

import java.util.Arrays;
import java.util.List;

public class SettingsActivity extends AbstractListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_generic);

        List<String> items  = Arrays.asList("Remove all assets");

        setUpSimpleList(items);
    }

    @Override
    public void clickItem(View v, TextView tv) {
        new AssetRepository(this.getApplication()).deleteAll();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
