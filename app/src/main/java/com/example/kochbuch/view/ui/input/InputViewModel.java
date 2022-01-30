package com.example.kochbuch.view.ui.input;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.kochbuch.enums.Foodtypes;
import com.example.kochbuch.model.Ingredient;
import com.example.kochbuch.model.Recipe;
import com.example.kochbuch.model.RecipeIngredient;
import com.example.kochbuch.storage.IngredientRepository;
import com.example.kochbuch.storage.RecipeRepository;

import java.util.ArrayList;
import java.util.List;

public class InputViewModel extends AndroidViewModel {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    public InputViewModel(Application application){
        super(application);
        this.recipeRepository = RecipeRepository.getRepository(application);
        this.ingredientRepository = IngredientRepository.getRepository(application);
    }

    public long insertRecipe(Recipe recipe){
        long recipeId = this.recipeRepository.insert(recipe);
        for (RecipeIngredient ri: recipe.getIngredients()) {
            ri.setRecipeId(recipeId);
            Ingredient ingredient = ri.getIngredient();
            long ingredientId = this.ingredientRepository.insert(ingredient);
            ri.setIngredientId(ingredientId);
            this.recipeRepository.insert(ri);
        }
        return recipeId;
    }

    public LiveData<List<Ingredient>> getIngredients(){
        return this.ingredientRepository.getIngredients();
    }

    public void deleteAll(){
        this.recipeRepository.deleteAllRecipes();
        this.recipeRepository.deleteAllRecipeIngredients();
        this.ingredientRepository.deleteAll();
    }

    public void generateTestData(){
        this.deleteAll();

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
        this.insertRecipe(recipe);

        //Beispele Rezepte 1

        Recipe recipe1 = new Recipe("Zitronensuppe mit Garnelen",
                "Garnelen bei Zimmertemperatur auftauen lassen. Möhren schälen, längs halbieren und in schräge Scheiben schneiden. Zuckerschoten in Streifen schneiden.\n" +
                        " Knoblauch halbieren und mit Brühe aufkochen. Mit Salz und Pfeffer würzen. Reis zugeben und zugedeckt bei milder Hitze 8 Min. quellen lassen.\n" +
                        " Möhren zugeben, kurz aufkochen und weitere 8 Min. bei milder Hitze garen. Zuckerschoten und Garnelen zugeben und 2 Min. mitgaren.\n" +
                        " Eier und Zitronensaft verrühren. Etwas Suppe unter Rühren zu den Eiern geben. Topf von der Herdplatte nehmen, Eimischung einrühren, die Suppe darf nicht mehr kochen! Mit Salz, Pfeffer und Zucker abschmecken.",
                "leckere Zitronensuppe mit Garnelen", Foodtypes.OMNIVORE);

        RecipeIngredient garnelenZ = new RecipeIngredient(150,new Ingredient("Garnelen",99));
        RecipeIngredient moehrenZ = new RecipeIngredient(200,new Ingredient("Möhren",35));
        RecipeIngredient zuckerschotenZ = new RecipeIngredient(100,new Ingredient("Zuckerschoten",80));
        Ingredient knoblauch = new Ingredient("Knoblauch",45);
        RecipeIngredient knoblauchZ = new RecipeIngredient(10,knoblauch);
        RecipeIngredient gemuesebrueheZ = new RecipeIngredient(800,new Ingredient("Gemüsebrühe",70));
        Ingredient salz = new Ingredient("Salz",1);
        RecipeIngredient salzZ = new RecipeIngredient(2,salz);
        Ingredient pfeffer = new Ingredient("Pfeffer",1);
        RecipeIngredient pfefferZ = new RecipeIngredient(2,pfeffer);
        RecipeIngredient langkornreisZ = new RecipeIngredient(70,new Ingredient("Langkornreis",100));
        RecipeIngredient eierZ = new RecipeIngredient(100,new Ingredient("Eier",50));
        RecipeIngredient zitronensaftZ = new RecipeIngredient(45,new Ingredient("Zitronensaft",30));
        RecipeIngredient zuckerZ = new RecipeIngredient(2,new Ingredient("Zucker",200));


        List<RecipeIngredient> recIngL1 = new ArrayList<>();
        recIngL1.add(garnelenZ);
        recIngL1.add(moehrenZ);
        recIngL1.add(zuckerschotenZ);
        recIngL1.add(knoblauchZ);
        recIngL1.add(gemuesebrueheZ);
        recIngL1.add(salzZ);
        recIngL1.add(pfefferZ);
        recIngL1.add(langkornreisZ);
        recIngL1.add(eierZ);
        recIngL1.add(zitronensaftZ);
        recIngL1.add(zuckerZ);

        recipe1.setIngredients(recIngL1);

        this.insertRecipe(recipe1);

        //Beispele Rezepte 2

        Recipe recipe2 = new Recipe("Überbackene Hähnchenbrust",
                "Zucchini in 1 cm dicke Scheiben schneiden. Paprikaschoten putzen und in grobe Spalten schneiden. Alles in eine Auflaufform geben, mit Salz, Pfeffer und Oregano würzen und mit Olivenöl beträufeln. Ofen auf 220 Grad (Umluft 200 Grad) vorheizen.\n" +
                        " Mozzarella abtropfen lassen, in 5 Scheiben schneiden und diese halbieren. Hähnchenbrustfilets jeweils in 1cm Abständen fünfmal 1,5–2 cm tief einschneiden. Rundum leicht salzen. Einschnitte mit je 1⁄2 Tl TomatenPesto und 1⁄2 Mozzarella-Scheibe füllen.\n" +
                        " Hähnchenbrustfilets auf das Gemüse setzen. Im heißen Ofen auf einem Rost auf der untersten Schiene 45 Min. überbacken.",
                "leckere Überbackene Hähnchenbrust", Foodtypes.OMNIVORE);

        RecipeIngredient zucchiniZ = new RecipeIngredient(360,new Ingredient("Zucchini",38));
        RecipeIngredient paprikagelbZ = new RecipeIngredient(120,new Ingredient("Paprikagelb",45));
        RecipeIngredient paprikarotZ = new RecipeIngredient(120,new Ingredient("Paprikarot",45));
        RecipeIngredient salzZ2 = new RecipeIngredient(2,salz);
        RecipeIngredient pfefferZ2 = new RecipeIngredient(2,pfeffer);
        RecipeIngredient oreganoZ = new RecipeIngredient(10,new Ingredient("Oregano",1));
        RecipeIngredient olivenoelZ = new RecipeIngredient(10,new Ingredient("Olivenöl",180));
        RecipeIngredient mozzarellaZ = new RecipeIngredient(100,new Ingredient("Mozzarella",210));
        RecipeIngredient haehnchenbrustfiletsZ = new RecipeIngredient(400,new Ingredient("Hähnchenbrustfilets",165));
        RecipeIngredient tomatenpestoZ = new RecipeIngredient(70,new Ingredient("Tomatenpesto",90));


        List<RecipeIngredient> recIngL2 = new ArrayList<>();
        recIngL2.add(zucchiniZ);
        recIngL2.add(paprikagelbZ);
        recIngL2.add(paprikarotZ);
        recIngL2.add(salzZ2);
        recIngL2.add(pfefferZ2);
        recIngL2.add(oreganoZ);
        recIngL2.add(olivenoelZ);
        recIngL2.add(mozzarellaZ);
        recIngL2.add(haehnchenbrustfiletsZ);
        recIngL2.add(tomatenpestoZ);


        recipe2.setIngredients(recIngL2);

        this.insertRecipe(recipe2);

        //Beispele Rezepte 3

        Recipe recipe3 = new Recipe("Flanksteak vom Grill mit Asia-Aromen",
                "Zitronengras am dicken Ende mit einem Plattiereisen oder einem schwerem Messer faserig klopfen. Ingwer und Knoblauch in dünne Scheiben schneiden. Mit Zitronengras, Sojasauce, Süßesauce und Kaffirlimettenblättern zu einer Marinade verrühren.\n" +
                        " Flanksteak in die Marinade legen und über Nacht abgedeckt im Kühlschrank marinieren.\n" +
                        " Flanksteak aus der Marinade nehmen und mit Küchenpapier trocken tupfen. Auf dem heißen Grill von jeder Seite 4-5 Minuten grillen, anschließend ca. 5 Minuten ruhen lassen.\n" +
                        " Das Steak quer zur Faser in 1 cm dicke Scheiben aufschneiden und mit etwas Pfeffer würzen. Dazu passt Rotkohlsalat mit Kokoschips.",
                "leckere Flanksteak vom Grill mit Asia-Aromen", Foodtypes.OMNIVORE);

        RecipeIngredient zitronengrasZ = new RecipeIngredient(100,new Ingredient("Zitronengras",38));
        RecipeIngredient ingwerZ = new RecipeIngredient(80,new Ingredient("Ingwer",35));
        RecipeIngredient knoblauchZ3 = new RecipeIngredient(40,knoblauch);
        RecipeIngredient sojasauceZ = new RecipeIngredient(50,new Ingredient("Sojasauce",19));

        RecipeIngredient suessesauceZ = new RecipeIngredient(150,new Ingredient("süßesauce",120));
        RecipeIngredient pfefferZ3 = new RecipeIngredient(2,pfeffer);
        RecipeIngredient kaffirlimettenblaetterZ = new RecipeIngredient(10,new Ingredient("Kaffirlimettenblätter",1));
        RecipeIngredient flanksteakZ = new RecipeIngredient(1000,new Ingredient("Flanksteak",237));



        List<RecipeIngredient> recIngL3 = new ArrayList<>();
        recIngL3.add(zitronengrasZ);
        recIngL3.add(ingwerZ);
        recIngL3.add(knoblauchZ3);
        recIngL3.add(sojasauceZ);
        recIngL3.add(suessesauceZ);
        recIngL3.add(pfefferZ3);
        recIngL3.add(kaffirlimettenblaetterZ);
        recIngL3.add(flanksteakZ);

        recipe3.setIngredients(recIngL3);

        this.insertRecipe(recipe3);
    }

}
