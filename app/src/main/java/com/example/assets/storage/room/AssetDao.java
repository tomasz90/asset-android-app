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

    @Insert
    void insert(Asset asset);

    @Update
    void update(Asset asset);

    @Delete
    void delete(Asset asset);

    @Query("DELETE FROM asset_table")
    void deleteAll();

    @Query("SELECT * FROM asset_table ORDER BY symbol DESC")
    LiveData<List<Asset>> getAll();
}
