package com.example.assets;

import android.content.Intent;
import android.os.Bundle;

import com.example.assets.fragments.FragmentList;
import com.example.assets.fragments.FragmentValues;
import com.example.assets.fragments.Adapter;
import com.example.assets.fragments.AssetDetailsTemplate;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();
        setRecyclerView();

        AssetDetailsTemplate template = new AssetDetailsTemplate(R.layout.asset_details_fragment, R.id.asset, R.id.unit_price, R.id.units, R.id.value);

        FragmentValues f1 = new FragmentValues("Platinum", "1445F", "3F","value");
        FragmentValues f2 = new FragmentValues("Gold", "1467F", "5F","valf");
        FragmentValues f3 = new FragmentValues("FF", "17F", "5F","valf");

        FragmentList list = new FragmentList(template).addFragments(f1, f2, f3);
        recyclerView.setAdapter(new Adapter(list));

        ExtendedFloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddAssetsActivity.class);
                startActivity(intent);
//                adapter.removeItem(0);
            }
        });
    }

    private void setRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.asset_list);
        recyclerView.setLayoutManager(layoutManager);
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
}
