package com.example.kochbuch.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

public class RecipeIngredient extends Basemodel {

    @NonNull
    @ColumnInfo(name = "ingredientId")
    private Ingredient ingredient;

    @NonNull
    @ColumnInfo(name = "quantityInG")
    private int quantityInG;


    @NonNull
    @Override
    public String toString() {
        return null;
    }

    @NonNull
    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(@NonNull Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getQuantityInG() {
        return quantityInG;
    }

    public void setQuantityInG(int quantityInG) {
        this.quantityInG = quantityInG;
    }
}
