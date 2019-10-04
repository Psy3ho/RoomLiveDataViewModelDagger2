package com.example.roomlivedataviewmodel.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.example.roomlivedataviewmodel.db.converters.DateConverter;
import com.example.roomlivedataviewmodel.db.entity.BorrowModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
@TypeConverters(DateConverter.class)
public interface BorrowModelDao {




    @Query("select * from BorrowModel where id = :id")
    BorrowModel getItembyId(String id);

    @Query("select * from BorrowModel")
    Flowable<List<BorrowModel>> getAllBorrowedItems();

    @Insert(onConflict = REPLACE)
    Completable addBorrow(BorrowModel borrowModel);

    @Delete
    void deleteBorrow(BorrowModel borrowModel);

}
