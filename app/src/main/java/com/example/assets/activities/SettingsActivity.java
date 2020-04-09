package com.example.assets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.assets.R;
import com.example.assets.activities.abstract_.AbstractListActivity;
import com.example.assets.fragments.FragmentTemplate;
import com.example.assets.fragments.FragmentValues;
import com.example.assets.util.StorageManager;

import java.util.Arrays;
import java.util.List;

public class SettingsActivity extends AbstractListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_generic);

        List<FragmentValues> values  = Arrays.asList(new FragmentValues("Remove all assets"));

        FragmentTemplate template = new FragmentTemplate(R.layout.fragment_generic, R.id.generic_asset);
        setUpList(R.id.generic_list, template, values);
    }

    @Override
    public void clickItem(View v, TextView tv) {
        StorageManager manager = new StorageManager(this);
        manager.deleteFile();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
