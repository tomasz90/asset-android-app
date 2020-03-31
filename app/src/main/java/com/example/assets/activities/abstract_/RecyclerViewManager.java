package com.example.assets.activities.abstract_;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assets.fragments.FragmentList;
import com.example.assets.fragments.FragmentTemplate;
import com.example.assets.fragments.FragmentValues;
import com.example.assets.util.ContentAdapter;

class RecyclerViewManager {

    private AbstractListActivity abstractListActivity;

    RecyclerViewManager(AbstractListActivity abstractListActivity) {
        this.abstractListActivity = abstractListActivity;
    }

    void setRecyclerView(int recycleViewId, FragmentTemplate template, FragmentValues... fragmentValues) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(abstractListActivity);
        RecyclerView recyclerView = abstractListActivity.findViewById(recycleViewId);
        recyclerView.setLayoutManager(layoutManager);
        FragmentList list = new FragmentList(template).addFragments(fragmentValues);
        ContentAdapter contentAdapter = new ContentAdapter(list);
        recyclerView.setAdapter(contentAdapter);
        contentAdapter.setOnItemClickListener((view, textView) -> abstractListActivity.clickItem(view, textView));
    }
}
