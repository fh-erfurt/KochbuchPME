package com.example.kochbuch.view.ui.recipelist;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.kochbuch.enums.Foodtypes;
import com.example.kochbuch.model.Recipe;
import com.example.kochbuch.storage.RecipeRepository;
import com.example.kochbuch.view.ui.recipedetail.RecipeDetailFragment;

import java.util.List;

// Viewmodel is used to pass data from the repository to the fragment
public class RecipeListViewModel extends AndroidViewModel {
    private final RecipeRepository recipeRepository;

    public RecipeListViewModel(Application application){
        super(application);
        this.recipeRepository = RecipeRepository.getRepository(application);
    }

    public LiveData<List<Recipe>> getRecipes(){
        return this.recipeRepository.getRecipes();
    }

    public LiveData<List<Recipe>> getFavorites(){
        return this.recipeRepository.getFavorites();
    }

}
