package com.example.kochbuch.view.ui.favorites;

import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StableIdKeyProvider;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kochbuch.R;
import com.example.kochbuch.model.Recipe;
import com.example.kochbuch.view.ui.recipelist.RecipeItemDetailsLookup;
import com.example.kochbuch.view.ui.recipelist.RecipeListAdapter;
import com.example.kochbuch.view.ui.recipelist.RecipeListViewModel;
import com.example.kochbuch.view.ui.core.BaseFragment;

import java.util.List;

public class FavoriteRecipeListFragment extends BaseFragment {
    private RecipeListViewModel recipeListViewModel;
    //Start Screen of the app
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){

        // inflate to view fragment_recipe_list
        this.recipeListViewModel = this.getViewModel(RecipeListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        System.out.println("generating testdata");
        //long success = recipeListViewModel.genTestData(); //TODO this can generate Testdata!

        // configure Recycler View
        RecyclerView recipeListView = root.findViewById(R.id.list_view_recipes);

        // view adapter
        final RecipeListAdapter adapter = new RecipeListAdapter(this.requireActivity(),
                recipeId -> {
                    Bundle args = new Bundle();
                    args.putLong("recipeId", recipeId);
                    NavController nc = NavHostFragment.findNavController( this );
                    nc.navigate( R.id.action_recipe_list_to_recipe_detail , args );
                });

        recipeListView.setAdapter(adapter);
        recipeListView.setLayoutManager(new LinearLayoutManager(this.requireActivity()));

        recipeListViewModel.getFavorites().observe(this.requireActivity(), adapter::submitList);
        return root;
    }
}
