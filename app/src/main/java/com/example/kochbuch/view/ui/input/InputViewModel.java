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

    public long insertRecipe(Recipe recipe){
        long recipeId = this.recipeRepository.insert(recipe);
        System.out.println("before loop");
        for (RecipeIngredient ri: recipe.getIngredients()) {
            ri.setRecipeId(recipeId);
            Ingredient ingredient = ri.getIngredient();
            System.out.println("ingredient Id" + ingredient.getId());
            long ingredientId = ingredient.getId();
            if(ingredientId == 0){
                ingredientId = this.ingredientRepository.insert(ingredient);
            }
            ri.setIngredientId(ingredientId);
            System.out.println("before insert");
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
    public Ingredient getIngredient(String name){
        return this.ingredientRepository.getIngredient(name);
    }
    public void deleteAll(){
        this.recipeRepository.deleteAllRecipes();
        this.recipeRepository.deleteAllRecipeIngredients();
        this.ingredientRepository.deleteAll();
    }



}
