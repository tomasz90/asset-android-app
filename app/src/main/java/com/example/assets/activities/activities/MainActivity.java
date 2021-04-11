package com.example.assets.activities.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assets.R;
import com.example.assets.activities.list_adapters.AssetDetailsAdapter;
import com.example.assets.util.Constants;
import com.example.assets.storage.room.entity.Asset;
import com.example.assets.storage.room.entity.AssetDetails;
import com.example.assets.storage.viewmodel.MainViewModel;
import com.example.assets.util.Dialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;
import java.util.Objects;

import lombok.SneakyThrows;

public class MainActivity extends AppCompatActivity {

    private MainViewModel assetViewModel;

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set activity, toolbar, tittle
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.main_activity_title);

        // Find all views
        RecyclerView recyclerView = findViewById(R.id.asset_list);
        ExtendedFloatingActionButton addAssetButton = findViewById(R.id.fab);
        TextView totalValue = findViewById(R.id.total_value);

        // Set list
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AssetDetailsAdapter adapter = new AssetDetailsAdapter();
        recyclerView.setAdapter(adapter);

        // Populate view with data
        assetViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        ProgressDialog loadingInfo = Dialog.displayLoading(this);
        assetViewModel.getAssetDetails().observe(this, assetsDetails -> {
            adapter.setAssetsDetails(assetsDetails);
            setTotalValue(assetsDetails, totalValue);
            loadingInfo.dismiss();
        });

        // Set swipe action
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Asset swipedAsset = adapter.getAssetAtPosition(viewHolder.getAdapterPosition());
                if (direction == ItemTouchHelper.RIGHT) {
                    new MaterialAlertDialogBuilder(MainActivity.this)
                            .setTitle(getString(R.string.want_to_remove_asset))
                            .setPositiveButton("Yes", (dialog, which) -> {
                                assetViewModel.deleteAsset(swipedAsset);
                                dialog.dismiss();
                                Dialog.displayToast(getApplication(), R.string.asset_removed);
                            })
                            .setNegativeButton("No", (dialog, which) -> {
                                dialog.dismiss();
                                assetViewModel.refresh();
                            })
                            .show();

                } else {
                    Intent intent = new Intent(MainActivity.this, AddAssetActivity.class);
                    intent.putExtra(Constants.EDITED_ASSET, swipedAsset);
                    startActivity(intent);
                }
            }
        }).attachToRecyclerView(recyclerView);

        // Add asset, start new activity
        addAssetButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AssetTypeListActivity.class);
            startActivity(intent);
        });

        // Update rates
        totalValue.setOnClickListener(v -> {
            assetViewModel.updateRates(true);
            System.out.println("from button ...............................................................................................");
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        assetViewModel.refresh();
    }

    private void setTotalValue(List<AssetDetails> assets, TextView totalValue) {
        float value = 0f;
        for (AssetDetails assetDetails : assets) {
            value += assetDetails.getValue();
        }
        totalValue.setText(getString(R.string.total_value_text_view, value, assets.get(0).getBaseCurrency()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem removeItem = menu.getItem(0);
        assetViewModel.getAssetDetails().observe(this, assetsDetails -> {
            boolean hasAnyItem = !assetsDetails.isEmpty();
            removeItem.setEnabled(hasAnyItem);
        });
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
                new MaterialAlertDialogBuilder(MainActivity.this)
                        .setTitle(getString(R.string.want_to_remove_all_assets))
                        .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                            assetViewModel.deleteAllAsset();
                            dialog.dismiss();
                            Dialog.displayToast(getApplication(), R.string.all_assets_removed);
                        })
                        .setNegativeButton(getString(R.string.no), (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .show();
                break;
            case R.id.action_change_base_currency:
                Intent intent = new Intent(this, ChooseBaseCurrencyActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
