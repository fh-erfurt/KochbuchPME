package com.example.kochbuch.view.ui.input;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

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

    private LiveData<List<Ingredient>> ingredientLiveData;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_input, container, false);
        viewModel = this.getViewModel( InputViewModel.class );
        this.hideBackButton();
        //was soll bei seitenaufbau pasieren
        viewModel.generateTestData();

        // TODO Eingabe von Rezepten
        // TODO Hinzufügen / auswählen von neuen/vorhandenen Zutaten
        // TODO beim auswählen mit typeahead


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.ingredientLiveData = viewModel.getIngredients();
        this.ingredientLiveData.observe(requireActivity(),this::updateView);
    }
    //

    @Override
    public void onPause() {
        super.onPause();
        this.ingredientLiveData.removeObservers(requireActivity());
    }
    // "main" methode
    // hier ist die ingredient Liste verfügbar
    private void updateView(List<Ingredient> ingredients){


    }
}
