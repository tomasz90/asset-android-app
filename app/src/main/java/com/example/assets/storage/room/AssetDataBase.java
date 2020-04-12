package com.example.assets.storage.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Asset.class, version = 2)
public abstract class AssetDataBase extends RoomDatabase {
    private static AssetDataBase instance;

    public abstract AssetDao assetDao();

    public static synchronized AssetDataBase getInstance(Context context){
        if (instance == null) {
            instance = Room
                    .databaseBuilder(context.getApplicationContext(), AssetDataBase.class, "asset_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
