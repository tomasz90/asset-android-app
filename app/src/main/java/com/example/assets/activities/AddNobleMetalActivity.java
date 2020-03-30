package com.example.assets.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assets.R;
import com.example.assets.RecyclerViewManager;
import com.example.assets.asset_types.MetalType;
import com.example.assets.fragments.FragmentTemplate;
import com.example.assets.fragments.FragmentValues;

public class AddNobleMetalActivity extends AppCompatActivity implements ActionOnClickItem{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_list);

        FragmentValues[] fragmentValues = {
                new FragmentValues(MetalType.GOLD),
                new FragmentValues(MetalType.SILVER),
                new FragmentValues(MetalType.PLATINUM),
                new FragmentValues(MetalType.PALLADIUM)};

        RecyclerViewManager manager = new RecyclerViewManager(this);
        FragmentTemplate assetDetailsTemplate = new FragmentTemplate(R.layout.generic_fragment, R.id.generic_asset);
        manager.setRecyclerView(this, R.id.generic_list, assetDetailsTemplate, fragmentValues);
    }

    @Override
    public void clickItem(TextView tv) {
        //nothing
    }
}
