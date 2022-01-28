package com.example.kochbuch.storage;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.kochbuch.model.RecipeIngredient;
import com.example.kochbuch.model.RecipeIngredientIngredient;

import java.util.List;

@Dao
public interface RecipeIngredientDao extends BaseDao<RecipeIngredient>{

    @Query("DELETE FROM RecipeIngredient")
    void deleteAll();

    @Transaction
    @Query("SELECT * FROM RecipeIngredient where recipeId=:recipeId")
    LiveData<List<RecipeIngredient>> getRecipeIngredients(long recipeId);

    @Transaction
    @Query("SELECT * FROM RecipeIngredient WHERE recipeId = :recipeId")
    LiveData<List<RecipeIngredientIngredient>> getRecipeIngredientsWithIngredient(long recipeId);

}
