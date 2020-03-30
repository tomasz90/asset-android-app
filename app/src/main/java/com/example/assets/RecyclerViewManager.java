package com.example.assets;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assets.activities.AbstractListActivity;
import com.example.assets.util.ContentAdapter;
import com.example.assets.fragments.FragmentList;
import com.example.assets.fragments.FragmentTemplate;
import com.example.assets.fragments.FragmentValues;

public class RecyclerViewManager {

    AbstractListActivity abstractListActivity;

    public RecyclerViewManager(AbstractListActivity abstractListActivity) {
        this.abstractListActivity = abstractListActivity;
    }

    public void setRecyclerView(int recycleViewId, FragmentTemplate template, FragmentValues... fragmentValues) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(abstractListActivity);
        RecyclerView recyclerView = abstractListActivity.findViewById(recycleViewId);
        recyclerView.setLayoutManager(layoutManager);
        FragmentList list = new FragmentList(template).addFragments(fragmentValues);
        ContentAdapter contentAdapter = new ContentAdapter(list);
        recyclerView.setAdapter(contentAdapter);
        contentAdapter.setOnItemClickListener(new ContentAdapter.OnItemListener() {
            @Override
            public void onItemClick(View v, TextView tv) {
               abstractListActivity.clickItem(v, tv);
            }
        });
    }
}
