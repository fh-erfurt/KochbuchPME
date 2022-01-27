package com.example.kochbuch.view.ui.browse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kochbuch.R;
import com.example.kochbuch.view.ui.core.BaseFragment;
import com.example.kochbuch.view.ui.recipelist.RecipeListAdapter;
import com.example.kochbuch.view.ui.recipelist.RecipeListViewModel;

public class BrowseFragment extends BaseFragment {
    private RecipeListViewModel recipeListViewModel;
    //TODO this is almost the same as FavoriteRecipeList except for line: 53
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){

        // inflate to view fragment_recipe_list
        this.recipeListViewModel = this.getViewModel(RecipeListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        System.out.println("generating testdata");

        // configure Recycler View
        RecyclerView recipeListView = root.findViewById(R.id.list_view_recipes);

        // view adapter calls the action to which detail entry we go
        final RecipeListAdapter adapter = new RecipeListAdapter(this.requireActivity(),
                recipeId -> {
                    Bundle args = new Bundle();
                    args.putLong("recipeId", recipeId);
                    NavController nc = NavHostFragment.findNavController( this );
                    nc.navigate( R.id.action_recipe_list_to_recipe_detail , args );
                });

        recipeListView.setAdapter(adapter);
        recipeListView.setLayoutManager(new LinearLayoutManager(this.requireActivity()));

        //LiveData<List<Recipe>> recipeList = recipeListViewModel.getRecipes();
        //finally sets the observer now we can actually use the LiveData livedata only works when observed!
        recipeListViewModel.getRecipes().observe(this.requireActivity(), adapter::submitList);
        return root;
    }
}
