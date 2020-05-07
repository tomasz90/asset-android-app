package com.example.assets.activities.list_adapters;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assets.R;
import com.example.assets.storage.room.entity.Asset;
import com.example.assets.storage.room.entity.AssetDetails;
import com.example.assets.storage.room.entity.BaseCurrency;

import java.util.ArrayList;
import java.util.List;

public class AssetDetailsAdapter extends RecyclerView.Adapter<AssetDetailsAdapter.AssetHolder> {

    private Pair<List<AssetDetails>, BaseCurrency> assetsDetails = Pair.create(new ArrayList<>(), null);

    public void setAssetsDetails(Pair<List<AssetDetails>, BaseCurrency> assetsDetails) {
        this.assetsDetails = assetsDetails;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AssetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_asset_details, parent, false);
        return new AssetHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssetHolder holder, int position) {
        Context c = holder.itemView.getContext();
        AssetDetails currentAsset = assetsDetails.first.get(position);
        BaseCurrency baseCurrency = assetsDetails.second;
        holder.symbol.setText(currentAsset.getSymbol());
        holder.additionalInfo.setText(currentAsset.getInfo());
        holder.quantity.setText(c.getString(R.string.float_two_decimal, currentAsset.getQuantity()));
        holder.rate.setText(c.getString(R.string.float_two_decimal_currency, currentAsset.getRate(), baseCurrency.getSymbol()));
        holder.value.setText(c.getString(R.string.float_no_decimal_currency, currentAsset.getValue(), baseCurrency.getSymbol()));
    }

    @Override
    public int getItemCount() {
        return assetsDetails.first.size();
    }

    public Asset getAssetAtPosition(int position) {
        return assetsDetails.first.get(position).getAsset();
    }

    static class AssetHolder extends RecyclerView.ViewHolder {
        TextView symbol;
        TextView additionalInfo;
        TextView quantity;
        TextView rate;
        TextView value;

        AssetHolder(@NonNull View itemView) {
            super(itemView);
            symbol = itemView.findViewById(R.id.asset);
            additionalInfo = itemView.findViewById(R.id.additional_info);
            quantity = itemView.findViewById(R.id.units);
            rate = itemView.findViewById(R.id.unit_price);
            value = itemView.findViewById(R.id.value);
        }
    }
}