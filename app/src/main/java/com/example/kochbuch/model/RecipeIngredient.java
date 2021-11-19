package com.example.kochbuch.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "RecipeIngredient",foreignKeys = {
        @ForeignKey(entity = Recipe.class,
                parentColumns = "id",
                childColumns = "recipeId",
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Ingredient.class,
            parentColumns = "id",
            childColumns = "ingredientId",
            onDelete = ForeignKey.CASCADE)
        })
public class RecipeIngredient extends Basemodel {

    @NonNull
    @ColumnInfo(name = "ingredientId", index = true)
    private int ingredientId;

    @NonNull
    @ColumnInfo(name = "recipeId", index = true)
    private int recipeId;

    @NonNull
    @ColumnInfo(name = "quantityInG")
    private int quantityInG;


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
}
