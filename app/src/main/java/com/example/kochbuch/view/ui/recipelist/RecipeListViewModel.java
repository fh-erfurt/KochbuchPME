package com.example.kochbuch.view.ui.recipelist;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;


import com.example.kochbuch.enums.Foodtypes;
import com.example.kochbuch.model.Recipe;
import com.example.kochbuch.storage.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends AndroidViewModel {
    private final RecipeRepository recipeRepository;

    public RecipeListViewModel(Application application){
        super(application);
        this.recipeRepository = RecipeRepository.getRepository(application);
    }

    public long genTestData(){
        if(this.recipeRepository.getRecipes().isEmpty()){
            Recipe recipe = new Recipe("Test1","cook for 59sec","1min noodles", Foodtypes.VEGETARIAN);
            return this.recipeRepository.insertAndWait(recipe);
        }
        return -1;
    }

    public List<Recipe> getRecipes(){
        return this.recipeRepository.getRecipes();
    }

    public List<Recipe> getVegetarian(){
        return this.recipeRepository.getByFoodtype(Foodtypes.VEGETARIAN);
    }

    public List<Recipe> getOmnivore(){
        return this.recipeRepository.getByFoodtype(Foodtypes.OMNIVORE);
    }

    public List<Recipe> getVegan(){
        return this.recipeRepository.getByFoodtype(Foodtypes.VEGAN);
    }

    public List<Recipe> getFavorites(){
        return this.recipeRepository.getFavorites();
    }
}
