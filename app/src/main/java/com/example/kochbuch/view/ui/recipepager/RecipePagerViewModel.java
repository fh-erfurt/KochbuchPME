package com.example.kochbuch.view.ui.recipepager;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.kochbuch.R;
import com.example.kochbuch.model.Recipe;
import com.example.kochbuch.storage.RecipeRepository;

import java.util.List;

public class RecipePagerViewModel extends AndroidViewModel {

    private final RecipeRepository recipeRepository;

    public RecipePagerViewModel(Application application) {
        super(application);
        this.recipeRepository = RecipeRepository.getRepository(application);
    }

    public LiveData<List<Recipe>> getRecipes(int usedList) {
        LiveData<List<Recipe>> erg = null;
        switch(usedList){
            case 0:
                erg = this.recipeRepository.getRecipes();
                break;
            case 1:
                erg = this.recipeRepository.getFavorites();
                break;
            case 2:
                erg = this.recipeRepository.getVegetarian();
                break;
            case 3:
                erg = this.recipeRepository.getOmnivore();
                break;
            case 4:
                erg = this.recipeRepository.getVegan();
                break;
        }

        return erg;
    }
}
