package com.example.assets;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assets.activities.ActionOnClickItem;
import com.example.assets.util.ContentAdapter;
import com.example.assets.fragments.FragmentList;
import com.example.assets.fragments.FragmentTemplate;
import com.example.assets.fragments.FragmentValues;

public class RecyclerViewManager {

    ActionOnClickItem actionOnClickItem;

    public RecyclerViewManager(ActionOnClickItem actionOnClickItem) {
        this.actionOnClickItem = actionOnClickItem;
    }

    public void setRecyclerView(AppCompatActivity activity, int recycleViewId, FragmentTemplate template, FragmentValues... fragmentValues) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        RecyclerView recyclerView = activity.findViewById(recycleViewId);
        recyclerView.setLayoutManager(layoutManager);
        FragmentList list = new FragmentList(template).addFragments(fragmentValues);
        ContentAdapter contentAdapter = new ContentAdapter(list);
        recyclerView.setAdapter(contentAdapter);
        contentAdapter.setOnItemClickListener(new ContentAdapter.OnItemListener() {
            @Override
            public void onItemClick(TextView tv) {
               actionOnClickItem.clickItem(tv);
            }
        });
    }
}
