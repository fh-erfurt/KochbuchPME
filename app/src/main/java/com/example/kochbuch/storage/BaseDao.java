package com.example.kochbuch.storage;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.kochbuch.model.Recipe;

import java.util.List;
@Dao
public interface BaseDao<T> {

    @Insert
    long insert(T insert);

    @Insert
    List<Long> insert(List<T> inserts);

    @Update
    void update(T... updates);

    @Update
    void update(List<T> updates);

    @Delete
    void delete(List<T> deletes);

    @Delete
    void delete(T... deletes);
}
