package com.example.kochbuch.storage;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import java.util.List;

/**
 * base dao all daos have these functions as default
 * @param <T>
 */
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
