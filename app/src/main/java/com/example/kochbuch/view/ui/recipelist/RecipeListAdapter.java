package com.example.kochbuch.view.ui.recipelist;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kochbuch.R;
import com.example.kochbuch.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeListAdapter extends ListAdapter<Recipe,RecipeListAdapter.RecipeViewHolder>{
    public interface RecipeClickListener{
        void onClick(long recipeId);
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder{
        private final TextView recipeName;
        private final ImageView recipeImage;
        private final ImageView selectionIcon;

        private long currentRecipeId = -1;
        // defines the Recipe RecyclerView
        private RecipeViewHolder(View itemView, RecipeClickListener recipeClickListener) {
            super(itemView);
            this.recipeName = itemView.findViewById(R.id.list_item_recipe_name);
            this.recipeImage = itemView.findViewById(R.id.list_item_recipe_image);
            this.selectionIcon = itemView.findViewById(R.id.list_item_select);
            itemView.setOnClickListener( v -> {
                recipeClickListener.onClick( this.currentRecipeId );
            });
        }

        public ItemDetailsLookup.ItemDetails<Long> getItemDetails() {
            return new ItemDetailsLookup.ItemDetails<Long>() {
                @Override
                public int getPosition() {
                    return getAdapterPosition();
                }

                @Override
                public Long getSelectionKey() {
                    return getItemId();
                }
            };
        }
    }

    private final LayoutInflater inflater;
    private final RecipeClickListener recipeClickListener;
    private SelectionTracker<Long> selectionTracker;
    // adapter is important
    public RecipeListAdapter(Context context, RecipeClickListener recipeClickListener) {
        super(new ListDiffCallback());
        this.inflater = LayoutInflater.from(context);
        this.recipeClickListener = recipeClickListener;
        this.setHasStableIds( true );
    }

    public void setSelectionTracker(SelectionTracker<Long> selectionTracker) {
        this.selectionTracker = selectionTracker;
    }
    // this too
    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = this.inflater.inflate(R.layout.list_item_recipe, parent, false);
        return new RecipeViewHolder(itemView, this.recipeClickListener);
    }
    // this probably also
    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        long itemId = this.getItemId(position);
        Recipe current = this.getRecipe(itemId);
        holder.currentRecipeId = current.getId();
        holder.recipeName.setText(String.format("%s",current.getName()));

        if( !selectionTracker.isSelected( itemId) ) {
            holder.selectionIcon.setImageResource(0);
        }
        else{
            holder.selectionIcon.setImageResource(R.drawable.ic_baseline_check_24);
        }


        Picasso p = Picasso.get();

        if(current.getPicturePath() != null && !current.getPicturePath().equals("")){
            p.load(current.getPicturePath())
                    .placeholder(R.drawable.recipe_preview_placeholder)
                    .error(R.drawable.icon_error)
                    .into( holder.recipeImage );
        }
    }
    @Override
    public long getItemId(int position) {
        return this.getCurrentList().get(position).getId();
    }


    public Recipe getItemById(long position){
        Recipe erg = null;
        int i = 0;
        for (Recipe recipe:this.getCurrentList()) {
            if(recipe.getId()==position){
                erg = recipe;
            }
            i++;
        }
        return erg;
    }

    public Recipe getRecipe( long position )
    {
        return getItemById(position);
    }
}
