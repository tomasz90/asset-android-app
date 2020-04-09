package com.example.assets.activities.abstract_;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assets.fragments.FragmentTemplate;
import com.example.assets.fragments.FragmentValues;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractListActivity extends AppCompatActivity implements ActionOnClickItem {

   public void setUpList(int listId, FragmentTemplate fragmentTemplate, List<FragmentValues> listFragmentValues) {

       FragmentValues[] fragmentValues = new FragmentValues[listFragmentValues.size()];
       // ArrayList to Array Conversion
       for (int i =0; i < listFragmentValues.size(); i++)
           fragmentValues[i] = listFragmentValues.get(i);

       RecyclerViewManager manager = new RecyclerViewManager(this);
       manager.setRecyclerView(listId, fragmentTemplate, fragmentValues);
   }
}
