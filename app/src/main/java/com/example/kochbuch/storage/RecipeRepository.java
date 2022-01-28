package com.example.kochbuch.storage;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.kochbuch.model.Recipe;
import com.example.kochbuch.model.RecipeIngredient;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class RecipeRepository {

    public static final  String LOG_TAG = "RecipeRepository";
    private final RecipeDao recipeDao;
    private final RecipeIngredientDao recipeIngredientDao;
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
        this.recipeIngredientDao = db.recipeIngredientDao();
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

    public long insert(RecipeIngredient recipeIngredient){
        try {
            return CookbookDatabase.executeWithReturn( () -> recipeIngredientDao.insert(recipeIngredient) );
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

    public LiveData<Recipe> getRecipe(long recipeId){
        LiveData<Recipe> recipe = this.queryLiveData(()->this.recipeDao.getRecipe(recipeId));
        return recipe;
    }

    public LiveData<List<Recipe>> getFavorites(){
        return this.queryLiveData(this.recipeDao::getFavorites);
    }

    public void update(Recipe recipe){
        CookbookDatabase.execute(()->recipeDao.update(this.prepareContactForWriting(recipe)));
    }

    public void deleteAllRecipes(){
        CookbookDatabase.execute(recipeDao::deleteAll);
    }
    public void deleteAllRecipeIngredients(){ CookbookDatabase.execute(recipeIngredientDao::deleteAll);}

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

    public LiveData<List<RecipeIngredient>> getRecipeIngredients(long recipeId) {
        return this.queryLiveData(()->this.recipeIngredientDao.getRecipeIngredients(recipeId));
    }
}
