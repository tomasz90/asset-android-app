package com.example.assets.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assets.R;
import com.example.assets.RecyclerViewManager;
import com.example.assets.asset_types.MetalType;
import com.example.assets.fragments.FragmentTemplate;
import com.example.assets.fragments.FragmentValues;

public class AddNobleMetalsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_list);

        FragmentTemplate assetDetailsTemplate = new FragmentTemplate(R.layout.generic_fragment, R.id.asset_type_name);

        FragmentValues[] fragmentValues = {
                new FragmentValues(MetalType.GOLD),
                new FragmentValues(MetalType.SILVER),
                new FragmentValues(MetalType.PLATINUM),
                new FragmentValues(MetalType.PALLADIUM)};

        RecyclerViewManager manager = new RecyclerViewManager();
        manager.setRecyclerView(this, R.id.list, assetDetailsTemplate, fragmentValues);
    }
}
