package com.example.assets.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assets.RecyclerViewManager;
import com.example.assets.fragments.FragmentTemplate;
import com.example.assets.fragments.FragmentValues;

public abstract class AbstractListActivity extends AppCompatActivity implements ActionOnClickItem {

   public void setUpList(int listId, FragmentTemplate fragmentTemplate, FragmentValues[] fragmentValues) {
       RecyclerViewManager manager = new RecyclerViewManager(this);
       manager.setRecyclerView(listId, fragmentTemplate, fragmentValues);
   }
}
