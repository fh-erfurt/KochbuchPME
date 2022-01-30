package com.example.kochbuch.view.ui.input;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.kochbuch.R;
import com.example.kochbuch.enums.Foodtypes;
import com.example.kochbuch.model.Ingredient;
import com.example.kochbuch.model.Recipe;
import com.example.kochbuch.model.RecipeIngredient;
import com.example.kochbuch.view.ui.core.BaseFragment;
import com.example.kochbuch.view.ui.recipedetail.RecipeDetailViewModel;

import java.util.ArrayList;
import java.util.List;

public class InputFragment extends BaseFragment {
    //TODO should let the user input new Recipes
    private InputViewModel viewModel;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_input, container, false);
        viewModel = this.getViewModel( InputViewModel.class );
        this.hideBackButton();
        //was soll bei seitenaufbau pasieren

        viewModel.deleteAll();


        Recipe recipe = new Recipe("Spagetti mit Tomatensoße",
                "In Kochtopf mit 1L Wasser werfen. Herd auf volle Pulle stellen und warten bis es Kocht.Die Nudel für 8min Kochen.Herd aus Stellen wasser aus Kochtopf abgießen. Tomatensoße und Käse drauf. Fertig!",
                "leckere Nudeln mit Tomatensoße", Foodtypes.VEGETARIAN);

        RecipeIngredient spagettiZ = new RecipeIngredient(150,new Ingredient("Spagetti",158));
        RecipeIngredient tomatensosseZ = new RecipeIngredient(100,new Ingredient("Tomatensoße",40));

        RecipeIngredient kaeseZ = new RecipeIngredient(40,new Ingredient("Parmesan",40));

        List<RecipeIngredient> recIngL = new ArrayList<>();
        recIngL.add(spagettiZ);
        recIngL.add(tomatensosseZ);
        recIngL.add(kaeseZ);

        recipe.setIngredients(recIngL);
        viewModel.insertRecipe(recipe);

       //Beispele Rezepte 1
/*
        Recipe recipe = new Recipe("Zitronensuppe mit Garnelen",
                 "Garnelen bei Zimmertemperatur auftauen lassen. Möhren schälen, längs halbieren und in schräge Scheiben schneiden. Zuckerschoten in Streifen schneiden.\n" +
                        " Knoblauch halbieren und mit Brühe aufkochen. Mit Salz und Pfeffer würzen. Reis zugeben und zugedeckt bei milder Hitze 8 Min. quellen lassen.\n" +
                        " Möhren zugeben, kurz aufkochen und weitere 8 Min. bei milder Hitze garen. Zuckerschoten und Garnelen zugeben und 2 Min. mitgaren.\n" +
                        " Eier und Zitronensaft verrühren. Etwas Suppe unter Rühren zu den Eiern geben. Topf von der Herdplatte nehmen, Eimischung einrühren, die Suppe darf nicht mehr kochen! Mit Salz, Pfeffer und Zucker abschmecken.",
                "leckere Zitronensuppe mit Garnelen", Foodtypes.OMNIVORE);

        RecipeIngredient garnelenZ = new RecipeIngredient(150,new Ingredient("Garnelen",99));
        RecipeIngredient möhrenZ = new RecipeIngredient(200,new Ingredient("Möhren",35));
        RecipeIngredient zuckerschotenZ = new RecipeIngredient(100,new Ingredient("Zuckerschoten",80));
        RecipeIngredient knoblauchZ = new RecipeIngredient(10,new Ingredient("Knoblauch",45));
        RecipeIngredient gemüsebrüheZ = new RecipeIngredient(800,new Ingredient("Gemüsebrühe",70));
        RecipeIngredient salzZ = new RecipeIngredient(2,new Ingredient("Salz",1));
        RecipeIngredient pfefferZ = new RecipeIngredient(2,new Ingredient("Pfeffer",1));
        RecipeIngredient langkornreisZ = new RecipeIngredient(70,new Ingredient("Langkornreis",100));
        RecipeIngredient eierZ = new RecipeIngredient(100,new Ingredient("Eier",50));
        RecipeIngredient zitronensaftZ = new RecipeIngredient(45,new Ingredient("Zitronensaft",30));
        RecipeIngredient zuckerZ = new RecipeIngredient(2,new Ingredient("Zucker",200));


        List<RecipeIngredient> recIngL = new ArrayList<>();
        recIngL.add(garnelenZ);
        recIngL.add(möhrenZ);
        recIngL.add(zuckerschotenZ);
        recIngL.add(knoblauchZ);
        recIngL.add(gemüsebrüheZ);
        recIngL.add(salzZ);
        recIngL.add(pfefferZ);
        recIngL.add(langkornreisZ);
        recIngL.add(eierZ);
        recIngL.add(zitronensaftZ);
        recIngL.add(zuckerZ);

        recipe.setIngredients(recIngL);

        viewModel.insertRecipe(recipe); */

        //Beispele Rezepte 2

       /* Recipe recipe = new Recipe("Überbackene Hähnchenbrust",
                "Zucchini in 1 cm dicke Scheiben schneiden. Paprikaschoten putzen und in grobe Spalten schneiden. Alles in eine Auflaufform geben, mit Salz, Pfeffer und Oregano würzen und mit Olivenöl beträufeln. Ofen auf 220 Grad (Umluft 200 Grad) vorheizen.\n" +
                         " Mozzarella abtropfen lassen, in 5 Scheiben schneiden und diese halbieren. Hähnchenbrustfilets jeweils in 1cm Abständen fünfmal 1,5–2 cm tief einschneiden. Rundum leicht salzen. Einschnitte mit je 1⁄2 Tl TomatenPesto und 1⁄2 Mozzarella-Scheibe füllen.\n" +
                         " Hähnchenbrustfilets auf das Gemüse setzen. Im heißen Ofen auf einem Rost auf der untersten Schiene 45 Min. überbacken.",
                "leckere Überbackene Hähnchenbrust", Foodtypes.OMNIVORE);

        RecipeIngredient zucchiniZ = new RecipeIngredient(360,new Ingredient("Zucchini",38));
        RecipeIngredient paprikagelbZ = new RecipeIngredient(120,new Ingredient("Paprikagelb",45));
        RecipeIngredient paprikarotZ = new RecipeIngredient(120,new Ingredient("Paprikarot",45));
        RecipeIngredient salzZ = new RecipeIngredient(2,new Ingredient("Salz",1));
        RecipeIngredient pfefferZ = new RecipeIngredient(2,new Ingredient("Pfeffer",1));
        RecipeIngredient oreganoZ = new RecipeIngredient(10,new Ingredient("Oregano",1));
        RecipeIngredient olivenölZ = new RecipeIngredient(10,new Ingredient("Olivenöl",180));
        RecipeIngredient mozzarellaZ = new RecipeIngredient(100,new Ingredient("Mozzarella",210));
        RecipeIngredient hähnchenbrustfiletsZ = new RecipeIngredient(400,new Ingredient("Hähnchenbrustfilets",165));
        RecipeIngredient tomatenpestoZ = new RecipeIngredient(70,new Ingredient("Tomatenpesto",90));


        List<RecipeIngredient> recIngL = new ArrayList<>();
        recIngL.add(zucchiniZ);
        recIngL.add(paprikagelbZ);
        recIngL.add(paprikarotZ);
        recIngL.add(salzZ);
        recIngL.add(pfefferZ);
        recIngL.add(oreganoZ);
        recIngL.add(olivenölZ);
        recIngL.add(mozzarellaZ);
        recIngL.add(hähnchenbrustfiletsZ);
        recIngL.add(tomatenpestoZ);


        recipe.setIngredients(recIngL);

        viewModel.insertRecipe(recipe); */

        //Beispele Rezepte 3
/*
        Recipe recipe = new Recipe("Flanksteak vom Grill mit Asia-Aromen",
                "Zitronengras am dicken Ende mit einem Plattiereisen oder einem schwerem Messer faserig klopfen. Ingwer und Knoblauch in dünne Scheiben schneiden. Mit Zitronengras, Sojasauce, Süßesauce und Kaffirlimettenblättern zu einer Marinade verrühren.\n" +
                        " Flanksteak in die Marinade legen und über Nacht abgedeckt im Kühlschrank marinieren.\n" +
                        " Flanksteak aus der Marinade nehmen und mit Küchenpapier trocken tupfen. Auf dem heißen Grill von jeder Seite 4-5 Minuten grillen, anschließend ca. 5 Minuten ruhen lassen.\n" +
                        " Das Steak quer zur Faser in 1 cm dicke Scheiben aufschneiden und mit etwas Pfeffer würzen. Dazu passt Rotkohlsalat mit Kokoschips.",
                "leckere Flanksteak vom Grill mit Asia-Aromen", Foodtypes.OMNIVORE);

        RecipeIngredient zitronengrasZ = new RecipeIngredient(100,new Ingredient("Zitronengras",38));
        RecipeIngredient ingwerZ = new RecipeIngredient(80,new Ingredient("Ingwer",35));
        RecipeIngredient knoblauchZ = new RecipeIngredient(40,new Ingredient("Knoblauch",45));
        RecipeIngredient sojasauceZ = new RecipeIngredient(50,new Ingredient("Sojasauce",19));
        RecipeIngredient süßesauceZ = new RecipeIngredient(150,new Ingredient("süßesauce",120));
        RecipeIngredient pfefferZ = new RecipeIngredient(2,new Ingredient("Pfeffer",1));
        RecipeIngredient kaffirlimettenblätterZ = new RecipeIngredient(10,new Ingredient("Kaffirlimettenblätter",1));
        RecipeIngredient flanksteakZ = new RecipeIngredient(1000,new Ingredient("Flanksteak",237));



        List<RecipeIngredient> recIngL = new ArrayList<>();
        recIngL.add(zitronengrasZ);
        recIngL.add(ingwerZ);
        recIngL.add(knoblauchZ);
        recIngL.add(sojasauceZ);
        recIngL.add(süßesauceZ);
        recIngL.add(pfefferZ);
        recIngL.add(kaffirlimettenblätterZ);
        recIngL.add(flanksteakZ);

        recipe.setIngredients(recIngL);

        viewModel.insertRecipe(recipe); */


        return root;
    }


    //TODO: resume, pause, update, usw...
}
