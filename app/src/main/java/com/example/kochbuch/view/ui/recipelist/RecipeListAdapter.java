package com.example.kochbuch.view.ui.recipelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kochbuch.R;
import com.example.kochbuch.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {
    public interface RecipeClickListener{
        void onClick(long recipeId);
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder{
        private final TextView recipeName;
        private final ImageView recipeImage;

        private long currentRecipeId = -1;

        private RecipeViewHolder(View itemView, RecipeClickListener recipeClickListener) {
            super(itemView);
            this.recipeName = itemView.findViewById(R.id.list_item_recipe_name);
            this.recipeImage = itemView.findViewById(R.id.list_item_recipe_image);

            itemView.setOnClickListener( v -> {
                recipeClickListener.onClick( this.currentRecipeId );
            });
        }
    }

    private final LayoutInflater inflater;
    private List<Recipe> recipeList;
    private final RecipeClickListener recipeClickListener;

    public RecipeListAdapter(Context context, RecipeClickListener recipeClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.recipeClickListener = recipeClickListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = this.inflater.inflate(R.layout.list_item_recipe, parent, false);
        return new RecipeViewHolder(itemView, this.recipeClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        if (this.recipeList != null && !this.recipeList.isEmpty()) {
            Recipe current = this.recipeList.get(position);

            holder.currentRecipeId = current.getId();
            holder.recipeName.setText(String.format("%s",current.getName()));

            Picasso p = Picasso.get();

            p.load(current.getPicturePath())
                    .placeholder(R.drawable.recipe_preview_placeholder)
                    .error(R.drawable.icon_error)
                    .into( holder.recipeImage );
        }
        else {
            // Covers the case of data not being ready yet.
            holder.recipeName.setText(R.string.text_empty_list);
        }
    }

    @Override
    public int getItemCount() {
        if( this.recipeList != null && !this.recipeList.isEmpty() )
            return this.recipeList.size();
        else
            return 0;
    }
    public void setRecipeList(List<Recipe> recipeList){
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }
}
