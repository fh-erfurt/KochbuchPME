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
        // we get the LifeData as RecipeWithIngredient List because there are multiple Ingredients in a recipe
        // that's why we have to map the RecipeIngredient Objects to the Recipe Objects
        // for that we use the function: merge() defined in RecipeWithIngredient
        // finally we return a List with one object in it: the Recipe filled with all the ingredient references
        // to actually get all the ingredients we have to manually get them later via the reference ids
        return Transformations.map(recipeWithIngredients, input -> input.stream().map(RecipeWithIngredient::merge).collect(Collectors.toList()) );
    }

    public LiveData<List<Recipe>> getFavorites(){
        return this.queryLiveData(this.recipeDao::getFavorites);
    }

    public void update(Recipe recipe){
        CookbookDatabase.execute(()->recipeDao.update(this.prepareContactForWriting(recipe)));
    }

    public void deleteAll(){
        CookbookDatabase.execute(recipeDao::deleteAll);
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

    private Recipe prepareContactForWriting(Recipe recipe ) {
        if( recipe.getCreated() < 0 )
            recipe.setCreated( System.currentTimeMillis() );

        recipe.setModified( recipe.getCreated() );
        recipe.setVersion( recipe.getVersion() + 1 );

        return recipe;
    }

}
