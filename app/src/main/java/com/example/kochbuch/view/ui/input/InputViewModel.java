package com.example.kochbuch.view.ui.input;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.kochbuch.model.Ingredient;
import com.example.kochbuch.model.Recipe;
import com.example.kochbuch.model.RecipeIngredient;
import com.example.kochbuch.storage.IngredientRepository;
import com.example.kochbuch.storage.RecipeRepository;

public class InputViewModel extends AndroidViewModel {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    public InputViewModel(Application application){
        super(application);
        this.recipeRepository = RecipeRepository.getRepository(application);
        this.ingredientRepository = IngredientRepository.getRepository(application);
    }

    public long insertRecipe(Recipe recipe){
        long recipeId = this.recipeRepository.insert(recipe);
        for (RecipeIngredient ri: recipe.getIngredients()) {
            ri.setRecipeId(recipeId);
            Ingredient ingredient = ri.getIngredient();
            long ingredientId = this.ingredientRepository.insert(ingredient);
            ri.setIngredientId(ingredientId);
            this.recipeRepository.insert(ri);
        }
        return recipeId;
    }

    public void deleteAll(){
        this.recipeRepository.deleteAllRecipes();
        this.recipeRepository.deleteAllRecipeIngredients();
        this.ingredientRepository.deleteAll();
    }

}
