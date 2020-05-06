package com.example.assets.activities.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assets.R;
import com.example.assets.activities.list_adapters.AssetDetailsAdapter;
import com.example.assets.constants.AssetConstants;
import com.example.assets.storage.room.Asset;
import com.example.assets.storage.room.AssetDetails;
import com.example.assets.storage.viewmodel.AssetViewModel;
import com.example.assets.util.ToastManager;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;
import java.util.Objects;

import lombok.SneakyThrows;

public class MainActivity extends AppCompatActivity {

    private TextView totalValue;
    private AssetViewModel assetViewModel;

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();

        RecyclerView recyclerView = findViewById(R.id.asset_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AssetDetailsAdapter adapter = new AssetDetailsAdapter();
        recyclerView.setAdapter(adapter);

        assetViewModel = new ViewModelProvider(this).get(AssetViewModel.class);
        ProgressDialog loadingInfo = ToastManager.displayLoading(this);
        assetViewModel.getAll().observe(this, assets -> {
            if (assets != null) {
                adapter.setAssets(assets);
                setTotalValue(assets);
                loadingInfo.dismiss();
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Asset swipedAsset = adapter.getAssetAtPosition(viewHolder.getAdapterPosition());
                if (direction == ItemTouchHelper.RIGHT) {
                    assetViewModel.delete(swipedAsset);
                } else {
                    Intent intent = new Intent(MainActivity.this, AddAssetActivity.class);
                    intent.putExtra(AssetConstants.ASSET_SYMBOL, swipedAsset.getSymbol());
                    intent.putExtra(AssetConstants.EDITED_ASSET, swipedAsset);
                    startActivity(intent);
                }
            }
        }).attachToRecyclerView(recyclerView);

        ExtendedFloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AssetTypeListActivity.class);
            startActivity(intent);
        });

        totalValue = findViewById(R.id.total_value);
        totalValue.setOnClickListener(v -> {
            assetViewModel.refreshDataFromCache(true);
            System.out.println("from button ...............................................................................................");
        });
    }

    private void setTotalValue(List<AssetDetails> assets) {
        float value = 0f;
        for (AssetDetails assetDetails : assets) {
            value += assetDetails.getValue();
        }
        if (!assets.isEmpty()) {
            totalValue.setText(getString(R.string.total_value_text_view, value, assets.get(0).getBaseCurrency().getSymbol()));
        } else {
            totalValue.setText(getString(R.string.total_value_text_view, value, "$"));
        }
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
        switch (id) {
            case R.id.action_remove:
                new AssetViewModel(this.getApplication()).deleteAll();
                break;
            case R.id.action_change_base_currency:
                Intent intent = new Intent(this, ChooseBaseCurrencyActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
