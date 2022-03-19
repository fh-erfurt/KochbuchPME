package com.example.kochbuch.view.ui.input;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LiveData;

import com.example.kochbuch.R;
import com.example.kochbuch.enums.Foodtypes;
import com.example.kochbuch.model.Ingredient;
import com.example.kochbuch.model.Recipe;
import com.example.kochbuch.model.RecipeIngredient;
import com.example.kochbuch.storage.FileSystemHandler;
import com.example.kochbuch.view.ui.core.BaseFragment;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * this fragment handles the input for new Recipes
 * only Ingredients that are already in the database can be selected
 *
 */
public class InputFragment extends BaseFragment {

    private static final int REQUEST_IMAGE_CAPTURE = 1337;
    private String currentPicturePath;

    private InputViewModel inputViewModel;
    private EditText nameField;
    private EditText instructionField;
    private EditText descriptionField;
    private ImageView picturePreview;
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
            System.out.println(this.currentPicturePath);
            newRecipe.setPicturePath( this.currentPicturePath );

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
            Button saveBtn = root.findViewById(R.id.button_input_recipe);
            this.addbtn = root.findViewById(R.id.button_input_ingredient);
            this.picturePreview = root.findViewById( R.id.picture_preview );
            this.picturePreview.setOnClickListener( this.picturePreviewClickListener );
            this.recipeIngredientList = new ArrayList<>();
            saveBtn.setOnClickListener(this.saveButtonClickListener);

        return root;
    }
    private final View.OnClickListener picturePreviewClickListener = v -> {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = FileSystemHandler.createImageFile( requireContext() );
                currentPicturePath = "file://" + photoFile.getAbsolutePath();
            } catch (IOException ex) {
                Toast.makeText(requireContext(), "Could not create file for image", Toast.LENGTH_SHORT ).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(requireContext(),
                        "com.example.kochbuch.fileprovider",
                        photoFile);
                takePictureIntent.putExtra( MediaStore.EXTRA_OUTPUT, photoURI );
                startActivityForResult( takePictureIntent, REQUEST_IMAGE_CAPTURE );
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            this.picturePreview.setImageURI( Uri.parse(currentPicturePath) );
        }
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



