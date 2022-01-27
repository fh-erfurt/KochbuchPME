package com.example.kochbuch.view.ui.recipelist;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.kochbuch.model.Recipe;

class ListDiffCallback extends DiffUtil.ItemCallback<Recipe> {
    // joa
    @Override
    public boolean areItemsTheSame(@NonNull Recipe oldItem, @NonNull Recipe newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Recipe oldItem, @NonNull Recipe newItem) {
        return oldItem.equals( newItem );
    }

}