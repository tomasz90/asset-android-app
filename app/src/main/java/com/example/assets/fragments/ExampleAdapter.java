package com.example.assets.fragments;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assets.R;

import java.util.List;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    List<Asset> assets;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {

        TextView asset;
        TextView unitPrice;
        TextView units;
        TextView value;


        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            asset = itemView.findViewById(R.id.asset);
            unitPrice = itemView.findViewById(R.id.unit_price);
            units = itemView.findViewById(R.id.units);
            value = itemView.findViewById(R.id.value);
        }
    }

    public ExampleAdapter(List<Asset> assets) {
        this.assets = assets;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        Asset asset = assets.get(position);
        holder.asset.setText(asset.getAssetName());
        holder.unitPrice.setText(String.format("%.02f",asset.getUnitPrice()) + " USD");
        holder.units.setText(String.valueOf(asset.getUnits()));
        holder.value.setText(String.valueOf(asset.getValue()) + " USD");

    }

    @Override
    public int getItemCount() {
        return assets.size();
    }
}
