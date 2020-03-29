package com.example.assets;

import android.content.Intent;
import android.os.Bundle;

import com.example.assets.fragments.Asset;
import com.example.assets.fragments.ExampleAdapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.main_activity_title);


        List<Asset> assets = new ArrayList<>();
        assets.add(new Asset("Platinum",  1445F, 3F));
        assets.add(new Asset("Silver", 14F, 150.2F));
        assets.add(new Asset("ETH", 140F, 25F));
        assets.add(new Asset("EUR", 1.1F, 3005F));
        assets.add(new Asset("PLN", 0.24F, 45300F));
        assets.add(new Asset("Silver", 14F, 150.2F));
        assets.add(new Asset("ETH", 140F, 25F));
        assets.add(new Asset("EUR", 1.1F, 3005F));
        assets.add(new Asset("PLN", 0.24F, 45300F));


        recyclerView = findViewById(R.id.asset_list);
        layoutManager = new LinearLayoutManager(this);
        adapter = new ExampleAdapter(assets);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        ExtendedFloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddAssetsActivity.class);
                startActivity(intent);
            }
        });
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
}
