package com.example.kochbuch.storage;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.kochbuch.model.Ingredient;

import java.util.List;

/**
 * IngredientDao
 */
@Dao
public interface IngredientDao extends BaseDao<Ingredient>{

    @Query("DELETE FROM Ingredient")
    void deleteAll();

    @Query("SELECT count(*) FROM Ingredient")
    int count();

    @Query("SELECT * FROM Ingredient")
    LiveData<List<Ingredient>> getIngredients();

    @Transaction
    @Query("SELECT * from Ingredient WHERE id=:id1 ")
    LiveData<Ingredient> getIngredient(long id1);

    @Transaction
    @Query("SELECT i.id,i.created,i.modified,i.version,i.name,i.kcal100 FROM Ingredient as i JOIN RecipeIngredient as ri ON i.id = ri.ingredientId WHERE ri.recipeId = :recipeId")
    LiveData<List<Ingredient>> getIngredients(long recipeId);

    @Query("SELECT * FROM Ingredient WHERE name = :name")
    Ingredient getIngredient(String name);

}
