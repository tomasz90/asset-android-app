package com.example.assets.activities.abstract_;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assets.R;
import com.example.assets.activities.list_adapters.SimpleItemAdapter;

import java.util.List;

public abstract class AbstractListActivity extends AppCompatActivity {

   public void setUpSimpleList(List<String> items) {

       RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
       RecyclerView recyclerView = this.findViewById(R.id.generic_list);
       recyclerView.setLayoutManager(layoutManager);
       SimpleItemAdapter simpleItemAdapter = new SimpleItemAdapter(items);
       recyclerView.setAdapter(simpleItemAdapter);
       simpleItemAdapter.setOnItemClickListener(this::clickItem);
   }

    public abstract void clickItem(View v, TextView tv);
}
