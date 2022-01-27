package com.example.kochbuch.view.ui.recipedetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.kochbuch.R;
import com.example.kochbuch.model.Recipe;
import com.example.kochbuch.view.ui.core.BaseFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeDetailFragment extends BaseFragment {
    public static final String ARG_RECIPE_ID = "recipeId";
    private RecipeDetailViewModel viewModel;
    private LiveData<List<Recipe>> recipeLiveData;


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
        //Recipe recipe = viewModel.getRecipe(recipeId);
        this.recipeLiveData.observe(requireActivity(),this::updateView);
    }

    @Override
    public void onPause() {
        super.onPause();
        this.recipeLiveData.removeObservers(requireActivity());

    }

    private void updateView(List<Recipe> recipe) {
        assert getView() != null;
        assert recipe != null;
        TextView titleView = getView().findViewById( R.id.fragment_recipe_detail_textViewTitle );
        TextView instructionView = getView().findViewById( R.id.fragment_recipe_detail_instruction );
        ImageView recipeImage = getView().findViewById( R.id.fragment_recipe_detail_image );
        TextView descriptionView = getView().findViewById(R.id.fragment_recipe_detail_description);

        titleView.setText(String.format("%s",recipe.get(0).getName()));
        instructionView.setText(String.format("%s",recipe.get(0).getInstruction()));
        descriptionView.setText(String.format("%s",recipe.get(0).getDescription()));

        System.out.println("sizeofArray: "+recipe.size());

        Picasso p = Picasso.get();

        p.load(recipe.get(0).getPicturePath())
                .error(R.drawable.icon_error)
                .fit()
                .into( recipeImage );
        System.out.println("detail site on " + recipe.get(0).getId());

    }
}
