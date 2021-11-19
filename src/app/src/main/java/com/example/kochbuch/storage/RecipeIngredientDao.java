package com.example.kochbuch.storage;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.kochbuch.model.Recipe;
import com.example.kochbuch.model.RecipeIngredient;

import java.util.List;
@Dao
public interface RecipeIngredientDao extends BaseDao<RecipeIngredient>{

    @Query("DELETE FROM RecipeIngredient")
    void deleteAll();

    @Query("SELECT count(*) FROM RecipeIngredient")
    int count();

    @Query("SELECT * from RecipeIngredient")
    List<RecipeIngredient> getRecipeIngredients();

    @Query("SELECT * from RecipeIngredient WHERE id=:id1 ")
    RecipeIngredient getEntryById(int id1);
}
