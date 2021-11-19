package com.example.kochbuch.storage;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.kochbuch.model.Ingredient;
import com.example.kochbuch.model.Recipe;

import java.util.List;
@Dao
public interface IngredientDao extends BaseDao<Ingredient>{

    @Query("DELETE FROM Ingredient")
    void deleteAll();

    @Query("SELECT count(*) FROM Ingredient")
    int count();

    @Query("SELECT * from Ingredient")
    List<Ingredient> getContacts();

    @Query("SELECT * from Ingredient ORDER BY id DESC LIMIT 1")
    Ingredient getLastEntry();

    @Query("SELECT * from Ingredient WHERE id=:id1 ")
    Ingredient getEntryById(int id1);
}
