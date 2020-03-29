package com.example.assets.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assets.fragments.FragmentList;
import com.example.assets.fragments.FragmentValues;

import java.util.ArrayList;
import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {
    FragmentList fragmentList;
    private OnItemListener listener;

    public interface OnItemListener {
        void onItemClick(TextView v);
    }

    public void setOnItemClickListener(OnItemListener listener) {
        this.listener = listener;
    }

   class ViewHolder extends RecyclerView.ViewHolder {

        List<TextView> tvs = new ArrayList<>();

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            //initialize views
            fragmentList.getTemplate().getInnerViewIds().forEach(id -> tvs.add(itemView.findViewById(id)));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(tvs.get(0));
                        }
                    }
                }
            });
        }
    }

    public ContentAdapter(FragmentList fragmentList) {
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(fragmentList.getTemplate().getMainViewId(), parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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


