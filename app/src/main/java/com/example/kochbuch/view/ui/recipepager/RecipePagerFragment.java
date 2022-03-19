package com.example.kochbuch.view.ui.recipepager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.kochbuch.R;
import com.example.kochbuch.core.Constants;
import com.example.kochbuch.model.Recipe;
import com.example.kochbuch.view.ui.core.BaseFragment;
import com.example.kochbuch.view.ui.recipedetail.RecipeDetailFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.Collections;
import java.util.List;

public class RecipePagerFragment extends BaseFragment {

    public static final String ARG_RECIPE_ID = "recipeId";
    public static final String ARG_USED_LIST = "usedList";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root =  inflater.inflate(R.layout.fragment_recipe_pager, container, false);
        RecipePagerViewModel viewModel = this.getViewModel(RecipePagerViewModel.class);
        // Find pager and TabLayout in our layout
        ViewPager2 pager = root.findViewById(R.id.recipe_pager_viewpager);
        // Configure pager and tab bar
        this.preparePager(pager, viewModel);
        this.hideBackButton();
        return root;
    }



    /**
     * Private helper to configure the View Pager
     * @param pager the pager
     * @param viewModel our screens view model
     */
    private void preparePager(ViewPager2 pager, RecipePagerViewModel viewModel) {
        assert getArguments() != null;
        RecipePagerAdapter adapter = new RecipePagerAdapter(this);
        pager.setAdapter( adapter );
        pager.setOffscreenPageLimit(4); // NEW! AND IMPORTANT!

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( requireContext() );
        boolean pagerZoomEnabled = sharedPreferences.getBoolean(Constants.PREF_PAGER_ZOOM, false);
        if( pagerZoomEnabled )
            pager.setPageTransformer( new ZoomOutPageTransformer() );


        viewModel.getRecipes(getArguments().getInt(ARG_USED_LIST)).observe(getViewLifecycleOwner(), recipes -> {
            adapter.setRecipes(recipes);
            long selectedRecipeId = getSelectedRecipeOrZero();
            pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
                /**
                 * sets the argument for the pager to the position of the currentItem, this is important to prevent the observer
                 * from jumping back to the first selected position, after changing the favorite entry in the recipe table
                 * @param position
                 */
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    Bundle args = new Bundle();
                    if(recipes.size()>0){
                        args.putLong("recipeId", recipes.get(position).getId());
                        setArguments(args);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    super.onPageScrollStateChanged(state);
                }
            });
            int selectedPosition = adapter.getSelectedRecipePosition( selectedRecipeId );
            pager.setCurrentItem( selectedPosition );
        });
    }

    /**
     * Private helper to get a valid index which should be display upon reaching that screen.
     * @return the selected page/quote index
     */
    private long getSelectedRecipeOrZero() {
        long selectedContactIndex = 0;
        if( getArguments() != null && getArguments().containsKey(ARG_RECIPE_ID) )
        {
            selectedContactIndex = getArguments().getLong(ARG_RECIPE_ID);
        }

        return selectedContactIndex;
    }


    private static class RecipePagerAdapter extends FragmentStateAdapter {

        private List<Recipe> recipes = Collections.emptyList();

        public RecipePagerAdapter(RecipePagerFragment fa) {
            super(fa);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Recipe r = this.recipes.get( position );
            return RecipeDetailFragment.newInstance( r.getId() );
        }


        @Override
        public int getItemCount() {
            return this.recipes.size();
        }

        public void setRecipes( List<Recipe> recipes )
        {
            this.recipes = recipes;
            notifyDataSetChanged();
        }

        public int getSelectedRecipePosition( long recipeId )
        {
            if( this.recipes != null ) {
                for( int i = 0; i < this.recipes.size(); i++ ) {
                    if( this.recipes.get( i ).getId() == recipeId ){
                        return i;
                    }
                }
            }
            return 0;
        }

        public List<Recipe> getRecipes() {
            return recipes;
        }
    }
}
