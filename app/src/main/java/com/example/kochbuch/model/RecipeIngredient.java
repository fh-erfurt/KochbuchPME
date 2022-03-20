package com.example.kochbuch.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

/**
 * this class is used for conecting a recipe with its ingredients
 * only additional information is the quantity of the given ingredient for the recipe
 */
@Entity(tableName = "RecipeIngredient")
public class RecipeIngredient extends Basemodel {

    @NonNull
    @ColumnInfo(name = "ingredientId")
    private long ingredientId;

    @NonNull
    @ColumnInfo(name = "recipeId")
    private long recipeId;

    @NonNull
    @ColumnInfo(name = "quantityInG")
    private int quantityInG;

    @Ignore
    private Ingredient ingredient;

    public RecipeIngredient(){

    }

    public RecipeIngredient(int quantityInG,Ingredient ingredient){
        this.quantityInG = quantityInG;
        this.ingredientId = ingredient.getId();
        this.ingredient = ingredient;
    }

    @NonNull
    @Override
    public String toString() {
        return null;
    }

    public long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long recipeId) {
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
