package com.example.assets.activities.list_adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assets.R;
import com.example.assets.storage.room.Asset;

import java.util.ArrayList;
import java.util.List;

public class AssetDetailsAdapter extends RecyclerView.Adapter<AssetDetailsAdapter.AssetHolder> {

    private List<Asset> assets = new ArrayList<>();

    @NonNull
    @Override
    public AssetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_asset_details, parent, false);
        return new AssetHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssetHolder holder, int position) {
        Asset currentAsset = assets.get(position);
        holder.symbol.setText(currentAsset.getSymbol());
        holder.additionalInfo.setText(currentAsset.getAdditionalInfo());
        holder.quantity.setText(String.valueOf(currentAsset.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return assets.size();
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
        notifyDataSetChanged();
    }

    class AssetHolder extends RecyclerView.ViewHolder {

        TextView symbol;
        TextView additionalInfo;
        TextView quantity;

        AssetHolder(@NonNull View itemView) {
            super(itemView);
            symbol = itemView.findViewById(R.id.asset);
            additionalInfo = itemView.findViewById(R.id.additional_info);
            quantity = itemView.findViewById(R.id.units);
        }
    }
}