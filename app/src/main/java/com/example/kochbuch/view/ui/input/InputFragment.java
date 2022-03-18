package com.example.kochbuch.view.ui.input;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

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
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class InputFragment extends BaseFragment {
    //TODO should let the user input new Recipes
    private InputViewModel inputViewModel;
    private EditText nameField;
    private EditText instructionField;
    private EditText descriptionField;
    private AutoCompleteTextView ingredientField;
    private EditText ingredientweightField;
    private Button addbtn;


    private List<RecipeIngredient> recipeIngredientList;

    private LiveData<List<Ingredient>> ingredientLiveData;


    private final View.OnClickListener saveButtonClickListener = v -> {

        if (v.getId() == R.id.button_input_recipe) {
            Recipe newRecipe = new Recipe(
                    nameField.getText().toString(),
                    instructionField.getText().toString(),
                    descriptionField.getText().toString(),
                    Foodtypes.VEGETARIAN);
            for (RecipeIngredient ringredient: this.recipeIngredientList){
                System.out.println(ringredient.getIngredient().getName() + " : " +ringredient.getQuantityInG() );
            }
            newRecipe.setIngredients(this.recipeIngredientList);
            String returnValue = this.inputViewModel.saveRecipe(newRecipe);

            hideKeyboard(this.requireContext(), v);
            Snackbar.make(v, returnValue, Snackbar.LENGTH_SHORT).show();
        }
    };
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

            inputViewModel = this.getViewModel(InputViewModel.class);

            View root = inflater.inflate(R.layout.fragment_input, container, false);

            this.nameField = root.findViewById(R.id.edit_input_recipe_name);
            this.instructionField = root.findViewById(R.id.edit_input_recipe_instruction);
            this.descriptionField = root.findViewById(R.id.edit_input_recipe_description);
            this.ingredientField = root.findViewById(R.id.edit_input_ingredient_name);
            this.ingredientweightField = root.findViewById(R.id.edit_input_ingredient_weight);
            //inputViewModel.generateTestData();
            //this.inputViewModel.generateTestData();
            Button saveBtn = root.findViewById(R.id.button_input_recipe);
            this.addbtn = root.findViewById(R.id.button_input_ingredient);
            this.recipeIngredientList = new ArrayList<>();
            saveBtn.setOnClickListener(this.saveButtonClickListener);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.ingredientLiveData = inputViewModel.getIngredients();
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

        String [] strings = new String[ingredients.size()];
        System.out.println(ingredients.size());
        int i = 0;
        for (Ingredient ingredient: ingredients)
        {
           strings[i] = ingredient.getName();
           i++;
        }
        for (int j = 0; j<ingredients.size(); j++)
        {
            System.out.println(strings[j]);
        }
        AutoCompleteTextView autoCompleteTextView = this.ingredientField;

        ArrayAdapter<String> adapter;

       adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_dropdown_item_1line,strings);
       autoCompleteTextView.setThreshold(1);
       autoCompleteTextView.setAdapter(adapter);

       autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener()
       {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        this.addbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            if (v.getId() == R.id.button_input_ingredient) {
                String ingredientName = ingredientField.getText().toString();
                int ingredientWeight = Integer.parseInt(ingredientweightField.getText().toString());
                RecipeIngredient newIngredient = null;
                for (Ingredient ingredient: ingredients){
                    if (ingredient.getName().equals(ingredientName) ){
                        newIngredient = new RecipeIngredient(ingredientWeight, ingredient);
                    }
                }

                recipeIngredientList.add(newIngredient);
                ingredientField.setText("");
                ingredientweightField.setText("");
                for (RecipeIngredient ringredient: recipeIngredientList){
                System.out.println(ringredient.getIngredient().getName() + " : " +ringredient.getQuantityInG() );
                }
            }
            }
        });
    }


        // ingredient liste
        // ingredients = Liste aller in der Datenbank vorhandenen Zutaten
}



