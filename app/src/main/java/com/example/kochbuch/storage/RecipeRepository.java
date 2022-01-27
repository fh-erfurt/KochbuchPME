package com.example.kochbuch.storage;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.kochbuch.model.Recipe;
import com.example.kochbuch.model.RecipeIngredient;
import com.example.kochbuch.model.RecipeWithIngredient;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class RecipeRepository {

    public static final  String LOG_TAG = "RecipeRepository";
    private final RecipeDao recipeDao;
    private LiveData<List<Recipe>> allRecipes;
    private  static RecipeRepository INSTANCE;


    public static RecipeRepository getRepository( Application application ) {
        if( INSTANCE == null ) {
            synchronized ( RecipeRepository.class ) {
                if( INSTANCE == null ) {
                    INSTANCE = new RecipeRepository( application );
                }
            }
        }
        return INSTANCE;
    }
    private RecipeRepository( Context context ) {
        CookbookDatabase db = CookbookDatabase.getDatabase( context );
        this.recipeDao = db.recipeDao();
    }
    public long insert(Recipe recipe){
        try {
            return CookbookDatabase.executeWithReturn( () -> recipeDao.insert(recipe) );
        }
        catch (ExecutionException | InterruptedException e)
        {
            e.printStackTrace();
        }

        return -1;
    }

    public LiveData<List<Recipe>> getRecipes(){
        this.allRecipes = this.queryLiveData(this.recipeDao::getRecipes);
        return this.allRecipes;
    }

    public LiveData<List<Recipe>> getRecipe(long recipeId){
        LiveData<List<RecipeWithIngredient>> recipeWithIngredients = this.queryLiveData(()->this.recipeDao.getRecipe(recipeId));
        return Transformations.map(recipeWithIngredients, input -> input.stream().map(RecipeWithIngredient::merge).collect(Collectors.toList()) );
    }

    private <T> LiveData<T> queryLiveData( Callable<LiveData<T>> query )
    {
        try {
            return CookbookDatabase.executeWithReturn( query );
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        // Well, is this a reasonable default return value?
        return new MutableLiveData<>();
    }

}
