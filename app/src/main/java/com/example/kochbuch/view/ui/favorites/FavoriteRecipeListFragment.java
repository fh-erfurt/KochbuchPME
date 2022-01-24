package com.example.kochbuch.view.ui.favorites;

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
import com.example.kochbuch.model.Recipe;
import com.example.kochbuch.view.ui.recipelist.RecipeListAdapter;
import com.example.kochbuch.view.ui.recipelist.RecipeListViewModel;
import com.example.kochbuch.view.ui.core.BaseFragment;

import java.util.List;

public class FavoriteRecipeListFragment extends BaseFragment {
    private RecipeListViewModel recipeListViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){

        recipeListViewModel = this.getViewModel(RecipeListViewModel.class);

        View root = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        RecyclerView recipeListView = root.findViewById(R.id.list_view_recipes);

        long success = recipeListViewModel.genTestData();
        System.out.println("success on "+success);


        List<Recipe> recipeList = recipeListViewModel.getRecipes();

        for (Recipe recipe:recipeList) {
            System.out.println(recipe.getName());
        }
        // view adapter
        final RecipeListAdapter adapter = new RecipeListAdapter(this.requireActivity(),
                recipeId -> {
                    Bundle args = new Bundle();
                    args.putLong("recipeId", recipeId);
                    NavController nc = NavHostFragment.findNavController( this );
                    nc.navigate( R.id.action_contact_list_to_contact_detail, args );
                });

        recipeListView.setAdapter(adapter);
        recipeListView.setLayoutManager(new LinearLayoutManager(this.requireActivity()));
        adapter.setRecipeList(recipeList);
        //System.out.println("recipe "+recipeList.get(0).getName());
        return root;
    }

}
