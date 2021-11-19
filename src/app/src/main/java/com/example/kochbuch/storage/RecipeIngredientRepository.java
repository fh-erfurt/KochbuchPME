package com.example.kochbuch.storage;

import android.app.Application;
import android.content.Context;

import com.example.kochbuch.model.Recipe;
import com.example.kochbuch.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class RecipeIngredientRepository {

    public static final  String LOG_TAG = "RecipeIngredientRepository";
    private final RecipeIngredientDao recipeIngredientDao;
    private  static RecipeIngredientRepository INSTANCE;

    public static RecipeIngredientRepository getRepository(Application application){
        if( INSTANCE == null ) {
            synchronized ( RecipeIngredientRepository.class ) {
                if( INSTANCE == null ) {
                    INSTANCE = new RecipeIngredientRepository( application );
                }
            }
        }

        return INSTANCE;
    }

    private RecipeIngredientRepository(Context context) {
        CookbookDatabase db = CookbookDatabase.getDatabase(context);
        this.recipeIngredientDao = db.recipeIngredientDao();
    }

    public List<RecipeIngredient> getIngredients(){
        return this.query(()->this.recipeIngredientDao.getRecipeIngredients());
    }

    private List<RecipeIngredient> query( Callable<List<RecipeIngredient>> query )
    {
        try {
            return CookbookDatabase.executeWithReturn( query );
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public void update(RecipeIngredient recipeIngredient) {
        CookbookDatabase.execute( () -> recipeIngredientDao.update( this.prepareRecipeIngredientForWriting(recipeIngredient) ) );
    }

    public void insert(RecipeIngredient recipeIngredient) {
        CookbookDatabase.execute( () -> recipeIngredientDao.insert( this.prepareRecipeIngredientForWriting(recipeIngredient) ) );
    }

    public long insertAndWait( RecipeIngredient recipeIngredient ) {

        try {
            return CookbookDatabase.executeWithReturn( () -> recipeIngredientDao.insert( this.prepareRecipeIngredientForWriting(recipeIngredient) ) );
        }
        catch (ExecutionException | InterruptedException e)
        {
            e.printStackTrace();
        }

        return -1;
    }

    private RecipeIngredient prepareRecipeIngredientForWriting( RecipeIngredient recipeIngredient ) {
        if( recipeIngredient.getCreated() < 0 )
            recipeIngredient.setCreated( System.currentTimeMillis() );

        recipeIngredient.setModified( recipeIngredient.getCreated() );
        recipeIngredient.setVersion( recipeIngredient.getVersion() + 1 );

        return recipeIngredient;
    }
}
