package com.example.kochbuch.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

import com.example.kochbuch.enums.Foodtypes;

import java.util.List;

public class Recipe extends Basemodel {

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "ingredients")
    private List<RecipeIngredient> ingredients;

    @NonNull
    @ColumnInfo(name = "instruction")
    private String instruction;

    @NonNull
    @ColumnInfo(name = "description")
    private String description;

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setIngredients(@NonNull List<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(@NonNull String instruction) {
        this.instruction = instruction;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public Foodtypes getRFoodtype(){
        Foodtypes type = Foodtypes.VEGAN;
        for (RecipeIngredient ingredient:ingredients) {
            if(type.ordinal() < ingredient.getIngredient().getFoodtype().ordinal() ){
                type = ingredient.getIngredient().getFoodtype();
            }
        }
        return type;
    }

    @NonNull
    @Override
    public String toString() {
        return null;
    }


}
