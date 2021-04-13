package com.example.assets.activity.list_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assets.R;
import com.example.assets.storage.room.entity.Asset;
import com.example.assets.storage.room.entity.AssetDetails;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

public class AssetDetailsAdapter extends RecyclerView.Adapter<AssetDetailsAdapter.AssetHolder> {

    private List<AssetDetails> assetsDetails = new ArrayList<>();

    public void setAssetsDetails(List<AssetDetails> assetsDetails) {
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
        AssetDetails currentAsset = assetsDetails.get(position);
        holder.symbol.setText(currentAsset.getSymbol());
        holder.additionalInfo.setText(currentAsset.getInfo());
        holder.quantity.setText(limitTo3MeaningDigits(currentAsset.getQuantity()));
        holder.rate.setText(c.getString(R.string.float_two_decimal_currency, currentAsset.getRate(), currentAsset.getBaseCurrency()));
        holder.value.setText(limitTo3MeaningDigits(currentAsset.getValue()) + " " + currentAsset.getBaseCurrency());
    }

    @Override
    public int getItemCount() {
        return assetsDetails.size();
    }

    public Asset getAssetAtPosition(int position) {
        return assetsDetails.get(position).getAsset();
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

    private static String limitTo3MeaningDigits(float f) {
        BigDecimal formatted;
        String suffix = "";
        int magnitude = (int) Math.log10(f);
        if (magnitude >= 6) {
            suffix = "M";
            formatted = new BigDecimal(f / 1000000).round(new MathContext(3));
        } else if (magnitude >= 3) {
            suffix = "K";
            formatted = new BigDecimal(f / 1000).round(new MathContext(3));
        } else if (magnitude >= 0) {
            formatted = new BigDecimal(f).round(new MathContext(3)).setScale(2 - magnitude);
        } else {
            formatted = new BigDecimal(f).round(new MathContext(2)).setScale(2 - magnitude);
        }
        return formatted.toPlainString() + suffix;
    }
}