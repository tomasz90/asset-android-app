package com.example.assets.activities.activities;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assets.R;
import com.example.assets.activities.list_adapters.SimpleItemAdapter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractListActivity extends AppCompatActivity {

    public void setUpSimpleList(Object[] rows) {
        List<String> rowsList = Arrays.stream(rows)
                .map(Object::toString)
                .collect(Collectors.toList());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = this.findViewById(R.id.generic_list);
        recyclerView.setLayoutManager(layoutManager);
        SimpleItemAdapter simpleItemAdapter = new SimpleItemAdapter();
        simpleItemAdapter.setItems(rowsList);
        recyclerView.setAdapter(simpleItemAdapter);
        simpleItemAdapter.setOnItemClickListener(this::clickItem);
    }

    public abstract void clickItem(View v, TextView tv);
}
