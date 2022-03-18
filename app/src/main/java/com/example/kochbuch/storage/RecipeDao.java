package com.example.kochbuch.storage;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.kochbuch.model.Recipe;

import java.util.List;
@Dao
public interface RecipeDao extends BaseDao<Recipe> {

    @Query("DELETE FROM Recipe")
    void deleteAll();

    @Query("SELECT count(*) FROM Recipe")
    int count();

    @Transaction
    @Query("SELECT * FROM Recipe ORDER BY id")
    LiveData<List<Recipe>> getRecipes();

    @Transaction
    @Query("SELECT * FROM Recipe WHERE id=:recipeId")
    LiveData<Recipe> getRecipe(long recipeId);

    @Transaction
    @Query("SELECT * FROM Recipe WHERE favorite = 1")
    LiveData<List<Recipe>> getFavorites();

    @Transaction
    @Query("SELECT * FROM Recipe WHERE foodtype = 'VEGETARIAN'")
    LiveData<List<Recipe>> getVegetarian();

    @Transaction
    @Query("SELECT * FROM Recipe WHERE foodtype = 'OMNIVORE'")
    LiveData<List<Recipe>> getOmnivore();

    @Transaction
    @Query("SELECT * FROM Recipe WHERE foodtype = 'VEGAN'")
    LiveData<List<Recipe>> getVegan();


}
