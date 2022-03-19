package com.example.kochbuch.view.ui.recipelist;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.kochbuch.model.Recipe;

class ListDiffCallback extends DiffUtil.ItemCallback<Recipe> {
    /**
     * compares the id of the given recipe objects
     * @param oldItem
     * @param newItem
     * @return
     */
    @Override
    public boolean areItemsTheSame(@NonNull Recipe oldItem, @NonNull Recipe newItem) {
        return oldItem.getId() == newItem.getId();
    }

    /**
     * compares the recipe contents vie the method in the Recipe class
     * @param oldItem
     * @param newItem
     * @return
     */
    @Override
    public boolean areContentsTheSame(@NonNull Recipe oldItem, @NonNull Recipe newItem) {
        return oldItem.equals( newItem );
    }

}