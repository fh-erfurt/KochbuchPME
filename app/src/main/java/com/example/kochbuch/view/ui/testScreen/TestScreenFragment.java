package com.example.kochbuch.view.ui.testScreen;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.kochbuch.R;
import com.example.kochbuch.model.Recipe;
import com.example.kochbuch.view.ui.core.BaseFragment;

import java.util.List;

public class TestScreenFragment extends BaseFragment {
    private TestScreenViewModel testScreenViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){

        testScreenViewModel = this.getViewModel(TestScreenViewModel.class);

        View root = inflater.inflate(R.layout.activity_main, container, false);
        //declared in fragment_home xml
        TextView textRecipes = root.findViewById(R.id.text_recipes);
        textRecipes.setMovementMethod(new ScrollingMovementMethod());

        long success = testScreenViewModel.genTestData();
        List<Recipe> recipeList = testScreenViewModel.getRecipes();

        StringBuilder sb = new StringBuilder();

        for (Recipe r:recipeList) {
            sb.append(r.getId()).append(": ")
                    .append( r.getName())
                    .append(" ")
                    .append(r.getInstruction())
                    .append(" ")
                    .append(r.getDescription())
                    .append("\n");
        }
        textRecipes.setText(sb.toString());
        return root;
    }

}
