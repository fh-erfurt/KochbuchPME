package com.example.kochbuch.storage;

import android.app.Application;
import android.content.Context;

import com.example.kochbuch.model.Ingredient;
import com.example.kochbuch.model.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class RecipeRepository {

    public static final  String LOG_TAG = "RecipeRepository";
    private final RecipeDao recipeDao;
    private  static RecipeRepository INSTANCE;

    public static RecipeRepository getRepository(Application application){
        if( INSTANCE == null ) {
            synchronized ( RecipeRepository.class ) {
                if( INSTANCE == null ) {
                    INSTANCE = new RecipeRepository( application );
                }
            }
        }

        return INSTANCE;
    }

    private RecipeRepository(Context context) {
        CookbookDatabase db = CookbookDatabase.getDatabase(context);
        this.recipeDao = db.recipeDao();
    }

    public List<Recipe> getRecipes(){
        return this.query(()->this.recipeDao.getRecipes());
    }

    private List<Recipe> query( Callable<List<Recipe>> query )
    {
        try {
            return CookbookDatabase.executeWithReturn( query );
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public void update(Recipe recipe) {
        CookbookDatabase.execute( () -> recipeDao.update( this.prepareRecipeForWriting(recipe) ) );
    }

    public void insert(Recipe recipe) {
        CookbookDatabase.execute( () -> recipeDao.insert( this.prepareRecipeForWriting(recipe) ) );
    }

    public long insertAndWait( Recipe recipe ) {

        try {
            return CookbookDatabase.executeWithReturn( () -> recipeDao.insert( this.prepareRecipeForWriting(recipe) ) );
        }
        catch (ExecutionException | InterruptedException e)
        {
            e.printStackTrace();
        }

        return -1;
    }

    private Recipe prepareRecipeForWriting( Recipe recipe ) {
        if( recipe.getCreated() < 0 )
            recipe.setCreated( System.currentTimeMillis() );

        recipe.setModified( recipe.getCreated() );
        recipe.setVersion( recipe.getVersion() + 1 );

        return recipe;
    }
}
