package com.example.kochbuch.view.ui.input;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.kochbuch.enums.Foodtypes;
import com.example.kochbuch.model.Ingredient;
import com.example.kochbuch.model.Recipe;
import com.example.kochbuch.model.RecipeIngredient;
import com.example.kochbuch.storage.IngredientRepository;
import com.example.kochbuch.storage.RecipeRepository;

import java.util.ArrayList;
import java.util.List;

public class InputViewModel extends AndroidViewModel {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    public InputViewModel(Application application){
        super(application);
        this.recipeRepository = RecipeRepository.getRepository(application);
        this.ingredientRepository = IngredientRepository.getRepository(application);
    }

    /**
     * first inserts the recipe and gets back the new id
     * then sets the new recipe id in every recipeIngredient and inserts it
     * @param recipe
     * @return the id of the new inserted Recipe
     */
    public long insertRecipe(Recipe recipe){
        long recipeId = this.recipeRepository.insert(recipe);
        for (RecipeIngredient ri: recipe.getIngredients()) {
            ri.setRecipeId(recipeId);
            this.recipeRepository.insert(ri);
        }
        return recipeId;
    }

    public String saveRecipe(Recipe recipe){
        long recipeId = insertRecipe(recipe);
        return "Recipe saved - "+ recipeId;
    }
    public LiveData<List<Ingredient>> getIngredients(){
        return this.ingredientRepository.getIngredients();
    }
}
