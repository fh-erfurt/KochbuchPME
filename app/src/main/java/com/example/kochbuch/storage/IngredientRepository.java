package com.example.kochbuch.storage;


import android.app.Application;
import android.content.Context;

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

    public List<Ingredient> getIngredients(){
        return this.query(()->this.ingredientDao.getContacts());
    }

    public Ingredient getIngredient(long id){
        return this.ingredientDao.getEntryById(id);
    }

    private List<Ingredient> query( Callable<List<Ingredient>> query )
    {
        try {
            return CookbookDatabase.executeWithReturn( query );
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public void update(Ingredient ingredient) {
        CookbookDatabase.execute( () -> ingredientDao.update( this.prepareIngredientForWriting(ingredient) ) );
    }

    public void insert(Ingredient ingredient) {
        CookbookDatabase.execute( () -> ingredientDao.insert( this.prepareIngredientForWriting(ingredient) ) );
    }

    public long insertAndWait( Ingredient ingredient ) {

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
