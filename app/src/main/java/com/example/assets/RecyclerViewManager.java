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

    ActionOnClickItem doInterface;

    public RecyclerViewManager(ActionOnClickItem doInterface) {
        this.doInterface = doInterface;
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
               doInterface.perform(tv);
            }
        });
    }
}
