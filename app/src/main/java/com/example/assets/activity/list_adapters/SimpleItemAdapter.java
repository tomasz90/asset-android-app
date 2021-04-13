package com.example.assets.activity.list_adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assets.R;

import java.util.List;

public class SimpleItemAdapter extends RecyclerView.Adapter<SimpleItemAdapter.SimpleItemHolder> {

    private List<String> items;
    private OnItemListener listener;

    public void setItems(List<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public SimpleItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_generic, parent, false);
        return new SimpleItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleItemHolder holder, int position) {
        // fill textViews with data
        String currentItem = items.get(position);
        holder.tv.setText(currentItem);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class SimpleItemHolder extends RecyclerView.ViewHolder {

        TextView tv;

        private SimpleItemHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.generic_asset);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(v, tv);
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemListener listener) {
        this.listener = listener;
    }

    public interface OnItemListener {
        void onItemClick(View v, TextView tv);
    }
}


