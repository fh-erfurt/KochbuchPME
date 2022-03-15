package com.example.kochbuch.view.ui.recipepager;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.kochbuch.model.Recipe;
import com.example.kochbuch.storage.RecipeRepository;

import java.util.List;

public class RecipePagerViewModel extends AndroidViewModel {

    private final RecipeRepository recipeRepository;

    public RecipePagerViewModel(Application application) {
        super(application);
        this.recipeRepository = RecipeRepository.getRepository(application);
    }

    public LiveData<List<Recipe>> getRecipes() {
        return this.recipeRepository.getRecipes();
    }
}
