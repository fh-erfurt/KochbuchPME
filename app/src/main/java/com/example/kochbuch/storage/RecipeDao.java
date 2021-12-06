package com.example.kochbuch.storage;


import androidx.room.Dao;
import androidx.room.Query;


import com.example.kochbuch.enums.Foodtypes;
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

    @Query("SELECT * from Recipe WHERE favorite = 1")
    List<Recipe> getFavorites();

    @Query("SELECT * from Recipe WHERE foodtype = :fdt1")
    List<Recipe> getByFoodtype(Foodtypes fdt1);
}
