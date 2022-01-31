package com.example.kochbuch.storage;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.kochbuch.model.Ingredient;
import com.example.kochbuch.model.Recipe;
import com.example.kochbuch.model.RecipeIngredient;

import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
    Our Database Management class

    Note the list of classes that represent entities. Here, we just have a single class: Contact.
    However, one can add more of needed. Any changes to the database like adding entities must
    result in a new, higher version number.
 */
@Database( entities = {Recipe.class, Ingredient.class, RecipeIngredient.class}, version = 2)
public abstract class CookbookDatabase extends RoomDatabase {

    private static final String LOG_TAG_DB = "ContactDB";

    /*
        Contact DAO reference, will be filled by Android
     */
    public abstract RecipeDao recipeDao();
    public abstract IngredientDao ingredientDao();
    public abstract RecipeIngredientDao recipeIngredientDao();
    /*
        Executor service to perform database operations asynchronous and independent from UI thread
     */
    private static final int NUMBER_OF_THREADS = 4;
    private static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool( NUMBER_OF_THREADS );

    /*
        Singleton Instance
     */
    private static volatile CookbookDatabase INSTANCE;

    /*
        Helper methods to ease external usage of ExecutorService
        e.g. perform async database operations
     */
    public static <T> T executeWithReturn(Callable<T> task)
            throws ExecutionException, InterruptedException
    {
        return databaseWriteExecutor.invokeAny( Collections.singletonList( task ) );
    }

    public static void execute( Runnable runnable )
    {
        databaseWriteExecutor.execute( runnable );
    }

    /*
        Singleton 'getInstance' method to create database instance thereby opening and, if not
        already done, init the database. Note the 'createCallback'.
     */
    static CookbookDatabase getDatabase(final Context context) {
        Log.i( LOG_TAG_DB, "getDatabase() called" );
        if (INSTANCE == null) {
            synchronized (CookbookDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CookbookDatabase.class, "cookbook_db")
                            .fallbackToDestructiveMigration()
                            //.addCallback(createCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /*
        Create DB Callback
        Used to add some initial data
     */
    /*private static final RoomDatabase.Callback createCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            Log.i( LOG_TAG_DB, "onCreate() called" );

            execute(() -> {
                ContactDao dao = INSTANCE.contactDao();
                dao.deleteAll();

                Faker faker = Faker.instance();
                for (int i = 0; i < 10; i++)
                {
                    Contact contact = new Contact(faker.name().lastName(), faker.name().firstName());
                    contact.setCreated( System.currentTimeMillis() );
                    contact.setModified( contact.getCreated() );
                    contact.setVersion( 1 );
                    dao.insert(contact);
                }
                Log.i(LOG_TAG_DB, "Inserted 10 values to DB");
            });
        }
    };*/
}
