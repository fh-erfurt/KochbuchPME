package com.example.kochbuch.storage;


import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.kochbuch.model.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class IngredientRepository {

    public static final  String LOG_TAG = "IngredientRepository";
    private final IngredientDao ingredientDao;
    private  static IngredientRepository INSTANCE;

    public static IngredientRepository getRepository(Application application){
        if( INSTANCE == null ) {
            synchronized ( IngredientRepository.class ) {
                if( INSTANCE == null ) {
                    INSTANCE = new IngredientRepository( application );
                }
            }
        }

        return INSTANCE;
    }

    private IngredientRepository(Context context) {
        CookbookDatabase db = CookbookDatabase.getDatabase(context);
        this.ingredientDao = db.ingredientDao();
    }

    public LiveData<List<Ingredient>> getIngredients(){
        return this.queryLiveData(this.ingredientDao::getIngredients);
    }

    public LiveData<List<Ingredient>> getIngredients(long recipeId){
        return this.queryLiveData(()->this.ingredientDao.getIngredients(recipeId));
    }

    public LiveData<Ingredient> getIngredient(long id){
        return this.queryLiveData(()->this.ingredientDao.getIngredient(id));
    }

    private <T> LiveData<T> queryLiveData(Callable<LiveData<T>> query )
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

    public void update(Ingredient ingredient) {
        CookbookDatabase.execute( () -> ingredientDao.update( this.prepareIngredientForWriting(ingredient) ) );
    }

    public void deleteAll(){
        CookbookDatabase.execute(ingredientDao::deleteAll);
    }

    public long insert( Ingredient ingredient ) {

        try {
            return CookbookDatabase.executeWithReturn( () -> ingredientDao.insert( this.prepareIngredientForWriting(ingredient) ) );
        }
        catch (ExecutionException | InterruptedException e)
        {
            e.printStackTrace();
        }

        return -1;
    }

    private Ingredient prepareIngredientForWriting( Ingredient ingredient ) {
        if( ingredient.getCreated() < 0 )
            ingredient.setCreated( System.currentTimeMillis() );

        ingredient.setModified( ingredient.getCreated() );
        ingredient.setVersion( ingredient.getVersion() + 1 );

        return ingredient;
    }

}
