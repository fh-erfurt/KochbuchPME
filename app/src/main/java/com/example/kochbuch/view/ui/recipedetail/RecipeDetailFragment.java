package com.example.kochbuch.view.ui.recipedetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.kochbuch.R;
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
    private TableLayout ingredientTable;
    private View localView;

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
        //this.localView = root;
        viewModel = this.getViewModel( RecipeDetailViewModel.class );

        assert getArguments() != null;
        long recipeId = getArguments().getLong(ARG_RECIPE_ID);
        this.recipeLiveData = viewModel.getRecipe(recipeId);
        this.recipeIngredientsLiveData = viewModel.getRecipeIngredients(recipeId);

        this.hideBackButton();
        return root;
    }

    @Override
    public View getView(){
        return super.getView();
    }



    /**
     * sets the observers for recipe and recipeIngredients
     */
    @Override
    public void onResume() {
        super.onResume();
        this.recipeIngredientsLiveData.observe(requireActivity(),this::updateViewRI);
        this.recipeLiveData.observe(requireActivity(),this::updateView);
    }
    @Override
    public void onStart() {
        super.onStart();
    }
    /**
     * here the table with the recipe Ingredients is build
     * @param recipeIngredients
     */
    private void updateViewRI(List<RecipeIngredient> recipeIngredients) {
        assert getView() != null;
        this.ingredientTable = getView().findViewById(R.id.ingredient_table);
        this.ingredientTable.setStretchAllColumns(true);
        this.ingredientTable.bringToFront();
        this.ingredientTable.removeAllViews();
        for (RecipeIngredient ri:recipeIngredients) {
            //System.out.println(ri.getIngredientId()+" "+ri.getIngredient().getName());
            // name
            TableRow tableRow = new TableRow(this.getContext());

            TextView txtName = new TextView(this.getContext());
            txtName.setTextSize(12);
            txtName.setText(String.format("%s",ri.getIngredient().getName()));
            tableRow.addView(txtName);
            // gramm
            TextView txtGramm = new TextView(this.getContext());
            txtGramm.setTextSize(12);
            txtGramm.setText(String.format("%s Gramm",(ri.getQuantityInG()) ));
            tableRow.addView(txtGramm);
            // kcal 100
            TextView txtkcal100 = new TextView(this.getContext());
            txtkcal100.setTextSize(12);
            txtkcal100.setText(String.format("%s kcal", ri.getIngredient().getKcal100()));
            tableRow.addView(txtkcal100);
            // kcal erechnet
            TextView txtkcal = new TextView(this.getContext());
            txtkcal.setTextSize(12);
            txtkcal.setText(String.format("%s kcal",(double)((int) ((double) ri.getQuantityInG() / 100 * ri.getIngredient().getKcal100()) * 100 ) / 100 ));
            tableRow.addView(txtkcal);
            this.ingredientTable.addView(tableRow);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        this.recipeLiveData.removeObservers(requireActivity());
        this.recipeIngredientsLiveData.removeObservers(requireActivity());
    }

    /**
     * here all the recipe data is pushed to the view and the favBtn changes the favorite state in the database for the current recipe
     * @param recipe
     */
    private void updateView(Recipe recipe) {
        assert getView() != null;
        assert recipe != null;
        // gets the elements in the Detail View
        TextView titleView = getView().findViewById( R.id.fragment_recipe_detail_textViewTitle );
        TextView instructionView = getView().findViewById( R.id.fragment_recipe_detail_instruction );
        ImageView recipeImage = getView().findViewById( R.id.fragment_recipe_detail_image );
        TextView descriptionView = getView().findViewById(R.id.fragment_recipe_detail_description);

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
                if(recipe.isFavorite()){
                    btnFavorite.setText(String.format("%s","vergessen"));
                }else{
                    btnFavorite.setText(String.format("%s","merken"));
                }
                viewModel.updateRecipe(recipe);
            }
        });
        if(recipe.isFavorite()){
            this.btnFavorite.setText(String.format("%s","vergessen"));
        }else{
            this.btnFavorite.setText(String.format("%s","merken"));
        }





        Picasso p = Picasso.get();
        if(recipe.getPicturePath() != null && !recipe.getPicturePath().equals("")) {
            p.load(recipe.getPicturePath())
                    .error(R.drawable.icon_error)
                    .fit()
                    .into( recipeImage );
            System.out.println("detail site on " + recipe.getId());
        }

    }
}
