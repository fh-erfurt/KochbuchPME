package com.example.kochbuch.view.ui.recipedetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.kochbuch.R;
import com.example.kochbuch.model.Ingredient;
import com.example.kochbuch.model.Recipe;
import com.example.kochbuch.model.RecipeIngredient;
import com.example.kochbuch.view.ui.core.BaseFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeDetailFragment extends BaseFragment {
    public static final String ARG_RECIPE_ID = "recipeId";
    private RecipeDetailViewModel viewModel;
    private LiveData<Recipe> recipeLiveData;
    private Button btnFavorite;
    private LiveData<List<RecipeIngredient>> recipeIngredientsLiveData;
    private LiveData<List<Ingredient>> ingredientsLiveData;

    // TODO either a dynamic Table or a RecylerView for ingredients


    public static  RecipeDetailFragment newInstance(long recipeId){
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_RECIPE_ID,recipeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        viewModel = this.getViewModel( RecipeDetailViewModel.class );
        this.hideBackButton();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        assert getArguments() != null;
        long recipeId = getArguments().getLong(ARG_RECIPE_ID);
        this.recipeLiveData = viewModel.getRecipe(recipeId);
        this.recipeIngredientsLiveData = viewModel.getRecipeIngredients(recipeId);
        this.ingredientsLiveData = viewModel.getIngredients(recipeId);

        this.recipeIngredientsLiveData.observe(requireActivity(),this::updateViewRI);
        this.ingredientsLiveData.observe(requireActivity(),this::updateViewI);
        this.recipeLiveData.observe(requireActivity(),this::updateView);

    }
    // update for ri TODO wenn zusammengefasst kann als recyclerview benutzt werden
    private void updateViewRI(List<RecipeIngredient> recipeIngredients) {
        for (RecipeIngredient ri:recipeIngredients) {
            System.out.println(ri.getIngredientId());
        }
    }
    // update for ingredients
    private void updateViewI(List<Ingredient> ingredients) {
        for (Ingredient i: ingredients) {
            System.out.println(i.getName()+" kcal:"+i.getKcal100());
        }
    }



    @Override
    public void onPause() {
        super.onPause();
        this.recipeLiveData.removeObservers(requireActivity());
        this.recipeIngredientsLiveData.removeObservers(requireActivity());
        this.ingredientsLiveData.removeObservers(requireActivity());
    }

    // main function for everything in the RecipeDetailView
    private void updateView(Recipe recipe) {
        assert getView() != null;
        assert recipe != null;
        // gets the elements in the Detail View
        TextView titleView = getView().findViewById( R.id.fragment_recipe_detail_textViewTitle );
        TextView instructionView = getView().findViewById( R.id.fragment_recipe_detail_instruction );
        ImageView recipeImage = getView().findViewById( R.id.fragment_recipe_detail_image );
        TextView descriptionView = getView().findViewById(R.id.fragment_recipe_detail_description);

        // gets the first element of the Recipe List because it is implemented that only the first element is actually filled
        // TODO: here all the Ingredient Data should be attached (not yet tested)
        System.out.println(recipe);
        // sets the Text elements of the xml file
        titleView.setText(String.format("%s",recipe.getName()));
        instructionView.setText(String.format("%s",recipe.getInstruction()));
        descriptionView.setText(String.format("%s",recipe.getDescription()));

        // gets the button to favorite or unfavorite a recipe
        this.btnFavorite = getView().findViewById(R.id.favorite_button);
        // click listener switches between favorite and unfavorite and applies the change as an update
        this.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipe.setFavorite(!recipe.isFavorite());
                System.out.println("recipe is currently: "+ recipe.isFavorite());
                if(recipe.isFavorite()){
                    btnFavorite.setText(String.format("%s","vergessen"));
                }else{
                    btnFavorite.setText(String.format("%s","merken"));
                }
                viewModel.updateRecipe(recipe);
            }
        });
        if(recipe.isFavorite()){
            btnFavorite.setText(String.format("%s","vergessen"));
        }else{
            btnFavorite.setText(String.format("%s","merken"));
        }





        Picasso p = Picasso.get();

        /*p.load(recipe.getPicturePath())
                .error(R.drawable.icon_error)
                .fit()
                .into( recipeImage );
        System.out.println("detail site on " + recipe.getId());*/

    }
}
