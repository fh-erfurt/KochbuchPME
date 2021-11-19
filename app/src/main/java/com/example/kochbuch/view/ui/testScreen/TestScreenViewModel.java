package com.example.kochbuch.view.ui.testScreen;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;


import com.example.kochbuch.R;
import com.example.kochbuch.model.Recipe;
import com.example.kochbuch.storage.RecipeRepository;

import java.util.List;

public class TestScreenViewModel extends AndroidViewModel {
    private final RecipeRepository recipeRepository;

    public TestScreenViewModel(Application application){
        super(application);
        this.recipeRepository = RecipeRepository.getRepository(application);
    }

    public long genTestData(){
        if(this.recipeRepository.getRecipes().isEmpty()){
            Recipe recipe = new Recipe("Test1","cook for 59sec","1min noodles");
            return this.recipeRepository.insertAndWait(recipe);
        }
        return -1;
    }

    public List<Recipe> getRecipes(){
        return this.recipeRepository.getRecipes();
    }
}
