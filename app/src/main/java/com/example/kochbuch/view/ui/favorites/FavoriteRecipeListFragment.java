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
import androidx.annotation.Nullable;
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
    private LiveData<List<Recipe>> recipes;
    private RecipeListAdapter adapter;
    //Start Screen of the app
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){

        // inflate to view fragment_recipe_list
        this.recipeListViewModel = this.getViewModel(RecipeListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        // configure Recycler View
        RecyclerView recipeListView = root.findViewById(R.id.list_view_recipes);

        // view adapter
        this.adapter = new RecipeListAdapter(this.requireActivity(),
                recipeId -> {
                    Bundle args = new Bundle();
                    args.putLong("recipeId", recipeId);
                    NavController nc = NavHostFragment.findNavController( this );
                    nc.navigate( R.id.action_recipe_list_to_recipe_detail , args );
                });

        recipeListView.setAdapter(this.adapter);
        recipeListView.setLayoutManager(new LinearLayoutManager(this.requireActivity()));
        this.recipes = recipeListViewModel.getRecipes();

        this.recipes.observe(this.requireActivity(), this.adapter::submitList);


        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.recipe_filter_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.recipes.removeObservers(this.requireActivity());

        switch(item.getItemId()){
            case R.id.select_favorites:
                System.out.println("this is favorites");
                this.recipes = recipeListViewModel.getFavorites();
                break;
            case R.id.select_vegetarian:
                System.out.println("this is vegetarisch");
                this.recipes = recipeListViewModel.getVegetarian();
                break;
            case R.id.select_meat:
                System.out.println("this is meat");
                this.recipes = recipeListViewModel.getOmnivore();
                break;
            case R.id.select_vegan:
                System.out.println("this is vegan");
                this.recipes = recipeListViewModel.getVegan();
                break;
        }
        this.recipes.observe(this.requireActivity(),this.adapter::submitList);
        return true;
        //return super.onOptionsItemSelected(item);
    }


}
