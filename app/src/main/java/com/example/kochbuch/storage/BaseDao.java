package com.example.kochbuch.storage;

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
    List<Long> insert(T... inserts);

    @Update
    void update(T... updates);

    @Delete
    void delete(T... deletes);

}
