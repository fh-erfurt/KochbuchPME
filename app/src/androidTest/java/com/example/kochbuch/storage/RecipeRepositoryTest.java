package com.example.kochbuch.storage;

import android.app.Application;
import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.kochbuch.model.Recipe;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class RecipeRepositoryTest extends TestCase {

    private RecipeRepository recipeRepository;

    public void setUp(Application application) throws Exception {
        super.setUp();
        this.recipeRepository = RecipeRepository.getRepository(application);
    }

    @Test
    public void insertTest(){
        Recipe recipe = new Recipe("Test1","cook ad 100 Degrees","doesn not taste good");
        long id = recipeRepository.insertAndWait(recipe);
        System.out.println("inserted Id:" + id);

        List<Recipe> entrys = recipeRepository.getIngredients();

        for (Recipe entry:entrys) {
            System.out.println(entry.getName()+" "+entry.getDescription()+" "+entry.getInstruction());
        }
    }

    public void tearDown() throws Exception {
    }
}