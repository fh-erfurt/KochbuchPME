package com.example.kochbuch.model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class RecipeIngredientIngredient {
    @Embedded
    public RecipeIngredient recipeIngredient;
    @Relation(parentColumn = "ingredientId",entityColumn = "id")
    public Ingredient ingredient;

    public RecipeIngredient merge(){
        this.recipeIngredient.setIngredient(ingredient);
        return this.recipeIngredient;
    }
}
