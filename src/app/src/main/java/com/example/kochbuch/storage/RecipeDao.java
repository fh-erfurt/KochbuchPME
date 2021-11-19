package com.example.kochbuch.storage;


import androidx.room.Dao;
import androidx.room.Query;


import com.example.kochbuch.model.Recipe;

import java.util.List;
@Dao
public interface RecipeDao extends BaseDao<Recipe> {

    @Query("DELETE FROM Recipe")
    void deleteAll();

    @Query("SELECT count(*) FROM Recipe")
    int count();

    @Query("SELECT * from Recipe")
    List<Recipe> getRecipes();

    @Query("SELECT * from Recipe ORDER BY id DESC LIMIT 1")
    Recipe getLastEntry();

    @Query("SELECT * from Recipe WHERE id=:id1 ")
    Recipe getEntryById(int id1);
}
