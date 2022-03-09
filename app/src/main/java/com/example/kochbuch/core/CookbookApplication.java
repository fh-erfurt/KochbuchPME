package com.example.kochbuch.core;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.kochbuch.storage.IngredientRepository;
import com.example.kochbuch.storage.RecipeRepository;

public class CookbookApplication extends Application {
    public static final String LOG_TAG = "AppClass";

    @Override
    public void onCreate() {
        super.onCreate();

        // Apply Theme
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this );
        boolean darkModeEnabled = sharedPreferences.getBoolean(Constants.PREF_DARK_MODE, false);
        AppCompatDelegate.setDefaultNightMode(
                darkModeEnabled ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );

        // Init Repo & Database
        RecipeRepository.getRepository(this);
        IngredientRepository.getRepository(this);
    }
}
