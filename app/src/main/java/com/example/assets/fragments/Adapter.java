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
    private Fragment fragment;
    private List<TextView> textViews = new ArrayList<>();

    class ExampleViewHolder extends RecyclerView.ViewHolder {

        private ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            //initialize views
            fragment.getViewIds().forEach(id -> textViews.add(itemView.findViewById(id)));
        }
    }

    public Adapter(Fragment fragment) {
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(fragment.getId(), parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        textViews.get(0).setText("XXXXXXXXXXXx");

    }

    @Override
    public int getItemCount() {
        return fragment.getSize();
    }

//
//    public void removeItem(int position){
//        assets.remove(position);
//        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, assets.size());
//    }
}


