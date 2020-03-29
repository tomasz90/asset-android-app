package com.example.assets.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ExampleViewHolder> {
    private FragmentList fragmentList;

    class ExampleViewHolder extends RecyclerView.ViewHolder {

        private List<TextView> tvs = new ArrayList<>();

        private ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            //initialize views
            fragmentList.getTemplate().getInnerViewIds().forEach(id -> tvs.add(itemView.findViewById(id)));
        }
    }

    public Adapter(FragmentList fragmentList) {
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(fragmentList.getTemplate().getMainViewId(), parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        // fill textViews with data
       FragmentValues fragmentValues = fragmentList.getFragmentValue(position);
       for(int i = 0; i < holder.tvs.size(); i++) {
           holder.tvs.get(i).setText(fragmentValues.get(i));
       }
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

//
//    public void removeItem(int position){
//        assets.remove(position);
//        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, assets.size());
//    }
}


