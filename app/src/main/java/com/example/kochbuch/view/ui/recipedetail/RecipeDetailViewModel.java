package com.example.kochbuch.view.ui.recipedetail;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.kochbuch.model.Ingredient;
import com.example.kochbuch.model.Recipe;
import com.example.kochbuch.model.RecipeIngredient;
import com.example.kochbuch.storage.IngredientRepository;
import com.example.kochbuch.storage.RecipeRepository;

import java.util.List;

public class RecipeDetailViewModel extends AndroidViewModel {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;


    public RecipeDetailViewModel(Application application){
        super(application);
        this.recipeRepository = RecipeRepository.getRepository(application);
        this.ingredientRepository = IngredientRepository.getRepository(application);
    }

    public LiveData<Recipe> getRecipe(long recipeId){
        return this.recipeRepository.getRecipe(recipeId);
    }
    public LiveData<List<RecipeIngredient>> getRecipeIngredients(long recipeId){
        return this.recipeRepository.getRecipeIngredients(recipeId);
    }

    public LiveData<List<Ingredient>> getIngredients(long recipeId){
        return this.ingredientRepository.getIngredients(recipeId);
    }

    public void updateRecipe(Recipe recipe){
        this.recipeRepository.update(recipe);
    }

    public LiveData<Ingredient> getIngredient(long ingredientId){
        return this.ingredientRepository.getIngredient(ingredientId);
    }

}
