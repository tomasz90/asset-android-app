package com.example.assets.storage.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Asset.class, version = 1)
public abstract class NoteDataBase extends RoomDatabase {
    private static NoteDataBase instance;

    public abstract AssetDao assetDao();

    public static synchronized NoteDataBase getInstance(Context context){
        if (instance == null) {
            instance = Room
                    .databaseBuilder(context.getApplicationContext(), NoteDataBase.class, "note_database")
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
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        AssetDao assetDao;

        private PopulateDbAsyncTask(NoteDataBase db) {
            this.assetDao = db.assetDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            assetDao.insert(new Asset("Symbol1", "Type1", 4f, "info"));
            assetDao.insert(new Asset("Symbol2", "Type1", 5f, "info"));
            assetDao.insert(new Asset("Symbol3", "Type1", 7f, "info"));
            return null;
        }
    }
}
