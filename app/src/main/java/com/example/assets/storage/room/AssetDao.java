package com.example.assets.storage.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AssetDao {

    default void upsert(Asset newAsset) {
        Asset oldAsset = getMatchingAsset(newAsset.getSymbol(), newAsset.getInfo());
        if (oldAsset != null) {
            oldAsset.setQuantity(oldAsset.getQuantity() + newAsset.getQuantity());
            update(oldAsset);
        } else {
            insert(newAsset);
        }
    }

    @Query("SELECT * FROM asset_table WHERE symbol = :symbol AND info = :info")
    Asset getMatchingAsset(String symbol, String info);

    @Insert
    void insert(Asset asset);

    @Update
    void update(Asset asset);

    @Delete
    void delete(Asset asset);

    @Query("DELETE FROM asset_table")
    void deleteAll();

    @Query("SELECT * FROM asset_table")
    LiveData<List<Asset>> getAll();
}
