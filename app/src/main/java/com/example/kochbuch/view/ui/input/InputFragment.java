package com.example.kochbuch.view.ui.input;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.kochbuch.R;
import com.example.kochbuch.enums.Foodtypes;
import com.example.kochbuch.model.Ingredient;
import com.example.kochbuch.model.Recipe;
import com.example.kochbuch.model.RecipeIngredient;
import com.example.kochbuch.view.ui.core.BaseFragment;
import com.example.kochbuch.view.ui.recipedetail.RecipeDetailViewModel;

import java.util.ArrayList;
import java.util.List;

public class InputFragment extends BaseFragment {
    //TODO should let the user input new Recipes
    private InputViewModel viewModel;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_input, container, false);
        viewModel = this.getViewModel( InputViewModel.class );
        this.hideBackButton();
        //was soll bei seitenaufbau pasieren

        viewModel.deleteAll();


        Recipe recipe = new Recipe("Spagetti mit Tomatensoße",
                "In Kochtopf mit 1L Wasser werfen. Herd auf volle Pulle stellen und warten bis es Kocht.Die Nudel für 8min Kochen.Herd aus Stellen wasser aus Kochtopf abgießen. Tomatensoße und Käse drauf. Fertig!",
                "leckere Nudeln mit Tomatensoße", Foodtypes.VEGETARIAN);

        RecipeIngredient spagettiZ = new RecipeIngredient(150,new Ingredient("Spagetti",158));
        RecipeIngredient tomatensosseZ = new RecipeIngredient(100,new Ingredient("Tomatensoße",40));

        RecipeIngredient kaeseZ = new RecipeIngredient(40,new Ingredient("Parmesan",40));

        List<RecipeIngredient> recIngL = new ArrayList<>();
        recIngL.add(spagettiZ);
        recIngL.add(tomatensosseZ);
        recIngL.add(kaeseZ);

        recipe.setIngredients(recIngL);

        viewModel.insertRecipe(recipe);


        return root;
    }

    //TODO: resume, pause, update, usw...
}
