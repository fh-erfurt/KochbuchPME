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

import java.util.ArrayList;
import java.util.List;

/**
 * main recycler view for the recipes
 */
public class FavoriteRecipeListFragment extends BaseFragment {
    private RecipeListViewModel recipeListViewModel;
    private LiveData<List<Recipe>> recipes;
    private RecipeListAdapter adapter;
    private int usedList = 0;
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
                    args.putInt("usedList",this.usedList);
                    NavController nc = NavHostFragment.findNavController( this );
                    nc.navigate( R.id.action_recipe_list_to_recipe_detail , args );
                }); // here the program navigates toe the recipePagerFragment with the given recipeId so the pager starts the detailFragment with it

        recipeListView.setAdapter(this.adapter);
        recipeListView.setLayoutManager(new LinearLayoutManager(this.requireActivity()));

        // selection handling
        SelectionTracker<Long> tracker = new SelectionTracker.Builder<>(
                "mySelectionId",
                recipeListView,
                new StableIdKeyProvider(recipeListView),
                new RecipeItemDetailsLookup(recipeListView),
                StorageStrategy.createLongStorage())
                .withSelectionPredicate(
                        SelectionPredicates.createSelectAnything()
                )
                .build();

        tracker.addObserver(new RecipeSelectionObserver(tracker,adapter));
        adapter.setSelectionTracker(tracker);
        //set recipes

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


    /**
     *
     * @param item the clicked item in the filter menu
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.recipes.removeObservers(this.requireActivity());

        switch(item.getItemId()){
            case R.id.select_all:
                this.recipes = recipeListViewModel.getRecipes();
                this.usedList = 0;
                break;
            case R.id.select_favorites:
                this.recipes = recipeListViewModel.getFavorites();
                this.usedList = 1;
                break;
            case R.id.select_vegetarian:
                this.recipes = recipeListViewModel.getVegetarian();
                this.usedList = 2;
                break;
            case R.id.select_meat:
                this.recipes = recipeListViewModel.getOmnivore();
                this.usedList = 3;
                break;
            case R.id.select_vegan:
                this.recipes = recipeListViewModel.getVegan();
                this.usedList = 4;
                break;
        }
        this.recipes.observe(this.requireActivity(),this.adapter::submitList);
        return true;
        //return super.onOptionsItemSelected(item);
    }


    private class RecipeSelectionObserver extends SelectionTracker.SelectionObserver<Long> {

        private final SelectionTracker<Long> tracker;
        private final RecipeListAdapter adapter;
        ActionMode actionMode;

        public RecipeSelectionObserver(SelectionTracker<Long> tracker, RecipeListAdapter adapter) {
            this.tracker = tracker;
            this.adapter = adapter;
        }

        @Override
        public void onSelectionChanged() {
            super.onSelectionChanged();

            Log.i("RecipeSelectionObserver", "Selection: " + tracker.getSelection());

            if (actionMode != null) return;

            actionMode = requireActivity().startActionMode(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.list_action_mode_menu, menu);
                    return true;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                    final List<Recipe> selectedRecipes = getSelectedRecipes();

                    if (item.getItemId() == R.id.list_action_delete) {
                        recipeListViewModel.deleteRecipes( selectedRecipes );
                    }
                    else if( item.getItemId() == R.id.list_action_favorite ) {
                        recipeListViewModel.favoriteRecipes( selectedRecipes );
                    }

                    if (actionMode != null)
                        actionMode.finish();

                    return true;
                }

                /*
                    Handle Action Mode Back Button
                 */
                @Override
                public void onDestroyActionMode( ActionMode mode ) {
                    tracker.clearSelection();
                    actionMode = null;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }
            });
        }

        private List<Recipe> getSelectedRecipes() {
            List<Recipe> selectedContacts = new ArrayList<>(tracker.getSelection().size());

            tracker.getSelection().iterator().forEachRemaining(aLong -> {

                Recipe c = adapter.getRecipe(aLong.intValue());

                Log.i("RecipeSelectionObserver", "Selected Recipe: " + aLong);

                selectedContacts.add(c);
            });

            return selectedContacts;
        }
    }



}
