package com.example.kochbuch.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class RecipeWithIngredient {
    @Embedded
    public Recipe recipe;

    @Relation(
            parentColumn = "id",
            entityColumn = "recipeId"
    )

    public List<RecipeIngredient> recipeIngredients;

    public Recipe merge()
    {
        this.recipe.setIngredients(recipeIngredients);
        return this.recipe;
    }
}
