package com.example.kochbuch.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

@Entity(tableName = "RecipeIngredient")
public class RecipeIngredient extends Basemodel {

    @NonNull
    @ColumnInfo(name = "ingredientId")
    private int ingredientId;

    @NonNull
    @ColumnInfo(name = "recipeId")
    private int recipeId;

    @NonNull
    @ColumnInfo(name = "quantityInG")
    private int quantityInG;

    @Ignore
    private Ingredient ingredient;


    @NonNull
    @Override
    public String toString() {
        return null;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getQuantityInG() {
        return quantityInG;
    }

    public void setQuantityInG(int quantityInG) {
        this.quantityInG = quantityInG;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}
