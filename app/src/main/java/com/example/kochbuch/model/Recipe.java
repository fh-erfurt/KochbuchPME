package com.example.kochbuch.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

import com.example.kochbuch.enums.Foodtypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(tableName = "Recipe")
public class Recipe extends Basemodel {
    // recipe Name
    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    // cooking instructions
    @NonNull
    @ColumnInfo(name = "instruction")
    private String instruction;
    // description of the dish
    @NonNull
    @ColumnInfo(name = "description")
    private String description;
    // a list with the needed ingredients in gram
    @Ignore
    private List<RecipeIngredient> ingredients = new ArrayList();
    // wether or not the dish is displayed in the favorites menu
    @ColumnInfo(name="favorite")
    private boolean favorite;
    // the path to the dish picture
    @ColumnInfo(name="picturePath")
    private String picturePath;

    @NonNull
    @ColumnInfo(name = "foodtype")
    private Foodtypes foodtype;

    public Foodtypes getFoodtype(){
        return this.foodtype;
    }
    public void setFoodtype(Foodtypes newtype){
        this.foodtype = newtype;
    }


    public Recipe(String name,String instruction,String description,Foodtypes foodtype){
        this.name = name;
        this.instruction = instruction;
        this.description = description;
        this.foodtype = foodtype;
        this.favorite = false;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
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


    public void setPicturePath(String picturePath){
        this.picturePath = picturePath;
    }

    public String getPicturePath(){
        return this.picturePath;
    }

    @Override
    public String toString() {
        return null;
    }


    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return name.equals(recipe.name) &&
                instruction.equals(recipe.instruction) &&
                picturePath.equals(recipe.picturePath);
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
