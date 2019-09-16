package com.example.roomlivedataviewmodel.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.roomlivedataviewmodel.db.dao.BorrowModelDao;
import com.example.roomlivedataviewmodel.db.entity.BorrowModel;

@Database(entities = {BorrowModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"borrow_db").build();
        }
        return INSTANCE;
    }

    public abstract BorrowModelDao itemAndPersonelModel();
}
