package com.example.kochbuch.view.ui.recipelist;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeItemDetailsLookup extends ItemDetailsLookup<Long> {
    private final RecyclerView recyclerView;
    // it makes the recyclerview do the stuff it does i dunno tbh
    public RecipeItemDetailsLookup(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
    }
    @Nullable
    @Override
    public ItemDetails<Long> getItemDetails(@NonNull MotionEvent event) {
        View view = recyclerView.findChildViewUnder(event.getX(), event.getY());

        if (view != null) {
            RecipeListAdapter.RecipeViewHolder holder =
                    (RecipeListAdapter.RecipeViewHolder)recyclerView.getChildViewHolder(view);

            return holder.getItemDetails();
        }

        return null;
    }

}
