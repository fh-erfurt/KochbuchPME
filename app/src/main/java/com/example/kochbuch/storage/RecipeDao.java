package com.example.kochbuch.storage;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.kochbuch.model.Recipe;
import com.example.kochbuch.model.RecipeIngredient;
import com.example.kochbuch.model.RecipeWithIngredient;

import java.util.List;
@Dao
public interface RecipeDao extends BaseDao<Recipe> {

    @Query("DELETE FROM Recipe")
    void deleteAll();

    @Query("SELECT count(*) FROM Recipe")
    int count();

    @Query("SELECT * FROM Recipe ORDER BY id")
    LiveData<List<Recipe>> getRecipes();

    @Query("SELECT * FROM Recipe WHERE id=:recipeId")
    LiveData<List<RecipeWithIngredient>> getRecipe(long recipeId);

}
