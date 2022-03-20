package com.example.kochbuch.storage;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.kochbuch.R;
import com.example.kochbuch.enums.Foodtypes;
import com.example.kochbuch.model.Ingredient;
import com.example.kochbuch.model.Recipe;
import com.example.kochbuch.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
    Our Database Management class

    Note the list of classes that represent entities. Here, we just have a single class: Contact.
    However, one can add more of needed. Any changes to the database like adding entities must
    result in a new, higher version number.
 */
@Database( entities = {Recipe.class, Ingredient.class, RecipeIngredient.class}, version = 4)
public abstract class CookbookDatabase extends RoomDatabase {

    private static final String LOG_TAG_DB = "ContactDB";

    /*
        Contact DAO reference, will be filled by Android
     */
    public abstract RecipeDao recipeDao();
    public abstract IngredientDao ingredientDao();
    public abstract RecipeIngredientDao recipeIngredientDao();
    /*
        Executor service to perform database operations asynchronous and independent from UI thread
     */
    private static final int NUMBER_OF_THREADS = 4;
    private static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool( NUMBER_OF_THREADS );

    /*
        Singleton Instance
     */
    private static volatile CookbookDatabase INSTANCE;

    /*
        Helper methods to ease external usage of ExecutorService
        e.g. perform async database operations
     */
    public static <T> T executeWithReturn(Callable<T> task)
            throws ExecutionException, InterruptedException
    {
        return databaseWriteExecutor.invokeAny( Collections.singletonList( task ) );
    }

    public static void execute( Runnable runnable )
    {
        databaseWriteExecutor.execute( runnable );
    }

    /*
        Singleton 'getInstance' method to create database instance thereby opening and, if not
        already done, init the database. Note the 'createCallback'.
     */
    static CookbookDatabase getDatabase(final Context context) {
        Log.i( LOG_TAG_DB, "getDatabase() called" );
        if (INSTANCE == null) {
            synchronized (CookbookDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CookbookDatabase.class, "cookbook_db")
                            .fallbackToDestructiveMigration()
                            .addCallback(createCallback) // here the testdata callback is called
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * callback for creating the testdata
     */
    private static final RoomDatabase.Callback createCallback = new RoomDatabase.Callback(){
        /**
         * onCreate is only called when the database is created the first time
         * @param db
         */
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            List<Ingredient> ingredients= new ArrayList<>();

            ingredients.add(new Ingredient("Apfel",52));
            ingredients.add(new Ingredient("Ananas",55));
            ingredients.add(new Ingredient("Aprikose",43));
            ingredients.add(new Ingredient("Birne",55));
            ingredients.add(new Ingredient("Banane",88));
            ingredients.add(new Ingredient("Blaubeeren",35));
            ingredients.add(new Ingredient("Blutorange",45));
            ingredients.add(new Ingredient("Brombeeren",43));
            ingredients.add( new Ingredient("Cranberries",46));
            ingredients.add( new Ingredient("Erdbeeren",32));
            ingredients.add( new Ingredient("Feige",107));
            ingredients.add( new Ingredient("Grapefruit",50));
            ingredients.add( new Ingredient("Granatapfel",74));
            ingredients.add( new Ingredient("Hagebutte",162));
            ingredients.add( new Ingredient("Honigmelone",54));
            ingredients.add( new Ingredient("Himbeeren",35));
            ingredients.add( new Ingredient("Ingwer",80));
            ingredients.add( new Ingredient("Kiwi",51));
            ingredients.add( new Ingredient("Kirschen",50));
            ingredients.add( new Ingredient("Litschi",66));
            ingredients.add( new Ingredient("Mandarine",50));
            ingredients.add( new Ingredient("mango",62));
            ingredients.add( new Ingredient("Maracuja",97));
            ingredients.add( new Ingredient("Pflaume",47));
            ingredients.add( new Ingredient("Pfirsich",41));
            ingredients.add( new Ingredient("Quitte",38));
            ingredients.add( new Ingredient("Rhabarber",21));
            ingredients.add( new Ingredient("Wassermelone",30));
            ingredients.add( new Ingredient("Weintraube",70));
            ingredients.add( new Ingredient("Zitrone",35));

            //Gemüse
            ingredients.add( new Ingredient("Aubergine",24));
            ingredients.add( new Ingredient("Artischocke",47));
            ingredients.add( new Ingredient("Avocado",160));
            ingredients.add( new Ingredient("Blumenkohl",25));
            ingredients.add( new Ingredient("Brokkoli",35));
            ingredients.add( new Ingredient("Bohnen",25));
            ingredients.add( new Ingredient("Brunnenkresse",19));
            ingredients.add( new Ingredient("Champignons",22));
            ingredients.add( new Ingredient("Chinakohl",13));
            ingredients.add( new Ingredient("Chili",40));
            ingredients.add( new Ingredient("Erbsen",82));
            ingredients.add( new Ingredient("Eisbergsalat",14));
            ingredients.add( new Ingredient("Fenchel",31));
            ingredients.add( new Ingredient("Gurke",15));
            ingredients.add( new Ingredient("Grünkohl",49));
            ingredients.add( new Ingredient("Karotte",36));
            ingredients.add( new Ingredient("Möhren",36));
            ingredients.add( new Ingredient("Kartoffel",86));
            ingredients.add( new Ingredient("Kohlrabi",27));
            ingredients.add( new Ingredient("Kürbis",19));
            ingredients.add( new Ingredient("Lauch",31));
            ingredients.add( new Ingredient("Mais",108));
            ingredients.add( new Ingredient("Mangold",19));
            ingredients.add( new Ingredient("Paprikagrün",17));
            ingredients.add( new Ingredient("Paprikagelb",28));
            ingredients.add( new Ingredient("Paprikarot",31));
            ingredients.add( new Ingredient("Radieschen",16));
            ingredients.add( new Ingredient("Rote Bete",43));
            ingredients.add( new Ingredient("Rotkohl",29));
            ingredients.add( new Ingredient("Rosenkohl",43));
            ingredients.add( new Ingredient("Rucola",25));
            ingredients.add( new Ingredient("Spargel",18));
            ingredients.add( new Ingredient("Spinat",23));
            ingredients.add( new Ingredient("Süßkartoffel",86));
            ingredients.add( new Ingredient("Zucchini",20));
            ingredients.add( new Ingredient("Zuckerschoten",58));
            ingredients.add( new Ingredient("Zwiebel",40));


            //Fleisch
            ingredients.add( new Ingredient("Bratwurst",375));
            ingredients.add( new Ingredient("Ente",375));
            ingredients.add( new Ingredient("Flanksteak",192));
            ingredients.add( new Ingredient("Hirsch",375));
            ingredients.add( new Ingredient("Hähnchenbrust",75));
            ingredients.add( new Ingredient("Kalbfleisch",94));
            ingredients.add( new Ingredient("Lamm",178));
            ingredients.add( new Ingredient("Putenbrust",111));
            ingredients.add( new Ingredient("Salami",507));
            ingredients.add( new Ingredient("Schinken",335));
            ingredients.add( new Ingredient("Speck",645));
            ingredients.add( new Ingredient("Rinderfilet",115));
            ingredients.add( new Ingredient("Rinderhack",212));
            ingredients.add( new Ingredient("Rumpsteak",162));
            ingredients.add( new Ingredient("Schweinefilet",171));
            ingredients.add( new Ingredient("Schweinefleisch, fett",311));
            ingredients.add( new Ingredient("Schweinefleisch, mager ",143));
            ingredients.add( new Ingredient("Schweineschnitzel",105));
            ingredients.add( new Ingredient("Wiener Würstchen",375));

            //Fisch
            ingredients.add( new Ingredient("Forelle",50));
            ingredients.add( new Ingredient("Garnelen",99));
            ingredients.add( new Ingredient("Hecht",50));
            ingredients.add( new Ingredient("Hering",146));
            ingredients.add( new Ingredient("Lachs",137));
            ingredients.add( new Ingredient("Rotbarschfilet",111));
            ingredients.add( new Ingredient("Seelachsfilet",83));
            ingredients.add( new Ingredient("Thunfisch",144));

            //Milchprodukte & Ei
            ingredients.add( new Ingredient("Buttermilch",28));
            ingredients.add( new Ingredient("Crème fraîche",292));
            ingredients.add( new Ingredient("Cheddar",403));
            ingredients.add( new Ingredient("Emmentaler",382));
            ingredients.add( new Ingredient("Edamer",251));
            ingredients.add( new Ingredient("Eier",155));
            ingredients.add( new Ingredient("Hüttenkäse",104));
            ingredients.add( new Ingredient("Kokosmilch",136));
            ingredients.add( new Ingredient("Milch",47));
            ingredients.add( new Ingredient("Magerquark",67));
            ingredients.add( new Ingredient("Mozzarella",280));
            ingredients.add( new Ingredient("Naturjoghurt",62));
            ingredients.add( new Ingredient("Parmesan",431));
            ingredients.add( new Ingredient("Sahne",204));
            ingredients.add( new Ingredient("Sauerrahm",162));
            ingredients.add( new Ingredient("Saure Sahne",115));
            ingredients.add( new Ingredient("Schmand",240));


            //Nudeln & reis
            ingredients.add( new Ingredient("Eiernudeln",369));
            ingredients.add( new Ingredient("Spätzle",352));
            ingredients.add( new Ingredient("Spaghetti",350));
            ingredients.add( new Ingredient("Reisnudeln",360));
            ingredients.add( new Ingredient("Vollkornnudeln",323));
            ingredients.add( new Ingredient("Sojanudeln",387));
            ingredients.add( new Ingredient("Bandnudeln",369));
            ingredients.add( new Ingredient("Penne",350));
            ingredients.add( new Ingredient("Langkornreis",277));


            //Gewürze

            ingredients.add( new Ingredient("Ajvar",86));
            ingredients.add( new Ingredient("Anis",337));
            ingredients.add( new Ingredient("Basilikum",23));
            ingredients.add( new Ingredient("Beifuß",42));
            ingredients.add( new Ingredient("Bockshornklee",323));
            ingredients.add( new Ingredient("Bohnenkraut",272));
            ingredients.add( new Ingredient("Borretsch",21));
            ingredients.add( new Ingredient("Bärlauch",77));
            ingredients.add( new Ingredient("Cayenne-Pfeffer",299));
            ingredients.add( new Ingredient("Peperoni",314));
            ingredients.add( new Ingredient("Curry",325));
            ingredients.add( new Ingredient("Dill",43));
            ingredients.add( new Ingredient("Estragon",295));
            ingredients.add( new Ingredient("Jalapeno",30));
            ingredients.add( new Ingredient("Kakao",433));
            ingredients.add( new Ingredient("Kaffirlimettenblaetter",67));
            ingredients.add( new Ingredient("Kapern",416));
            ingredients.add( new Ingredient("Kardamon",311));
            ingredients.add( new Ingredient("Knoblauch",149));
            ingredients.add( new Ingredient("Kümmel",311));
            ingredients.add( new Ingredient("Majoran",371));
            ingredients.add( new Ingredient("Minze",285));
            ingredients.add( new Ingredient("Muskatnuss",525));
            ingredients.add( new Ingredient("Oregano",306));
            ingredients.add( new Ingredient("Pfeffer",255));
            ingredients.add( new Ingredient("Salz",0));
            ingredients.add( new Ingredient("Thymian",101));
            ingredients.add( new Ingredient("Trüffel",48));
            ingredients.add( new Ingredient("Zimt",248));
            ingredients.add( new Ingredient("Zitronengras",99));
            ingredients.add( new Ingredient("Zitronensaft",29));
            ingredients.add( new Ingredient("Zucker",409));

            //Sosse

            ingredients.add( new Ingredient("Tomatensosse",29));
            ingredients.add( new Ingredient("Gemüsebrühe",3));
            ingredients.add( new Ingredient("Tomatenpesto",426));
            ingredients.add( new Ingredient("Sojasauce",53));
            ingredients.add( new Ingredient("Süssesauce",53));

            //Oil
            ingredients.add( new Ingredient("Olivenöl",884));
            ingredients.add( new Ingredient("Sonnenblumenöl",884));



            execute(()->{
                IngredientDao idao = INSTANCE.ingredientDao();
                idao.insert(ingredients);

                // spaghetti
                Recipe spaghetti = new Recipe("Spagetti mit Tomatensoße",
                        "In Kochtopf mit 1L Wasser werfen. Herd auf volle Pulle stellen und warten bis es Kocht.Die Nudel für 8min Kochen.Herd aus Stellen wasser aus Kochtopf abgießen. Tomatensoße und Käse drauf. Fertig!",
                        "leckere Nudeln mit Tomatensoße", Foodtypes.VEGETARIAN);
                RecipeIngredientDao riDao = INSTANCE.recipeIngredientDao();
                RecipeDao rdao = INSTANCE.recipeDao();
                Uri path = Uri.parse("android.resource://com.example.kochbuch/"+R.drawable.spaghetti);
                spaghetti.setPicturePath(path.toString());
                rdao.insert(spaghetti);
                long spaghettiId = rdao.getRecipeIdByName("Spagetti mit Tomatensoße");
                List<RecipeIngredient>  recipeIngredients = new ArrayList<>();
                recipeIngredients.add(new RecipeIngredient(100,idao.getIngredient("Spaghetti")));
                recipeIngredients.add(new RecipeIngredient(50,idao.getIngredient("Tomatensosse")));
                recipeIngredients.add(new RecipeIngredient(20,idao.getIngredient("Parmesan")));
                for (RecipeIngredient ri:recipeIngredients) {
                    ri.setRecipeId(spaghettiId);
                }
                riDao.insert(recipeIngredients);
                // clears the ingredients
                recipeIngredients.clear();

                // zitronensuppe
                Recipe zitronensuppe = new Recipe("Zitronensuppe mit Garnelen",
                        "Garnelen bei Zimmertemperatur auftauen lassen. Möhren schälen, längs halbieren und in schräge Scheiben schneiden. Zuckerschoten in Streifen schneiden.\n" +
                                " Knoblauch halbieren und mit Brühe aufkochen. Mit Salz und Pfeffer würzen. Reis zugeben und zugedeckt bei milder Hitze 8 Min. quellen lassen.\n" +
                                " Möhren zugeben, kurz aufkochen und weitere 8 Min. bei milder Hitze garen. Zuckerschoten und Garnelen zugeben und 2 Min. mitgaren.\n" +
                                " Eier und Zitronensaft verrühren. Etwas Suppe unter Rühren zu den Eiern geben. Topf von der Herdplatte nehmen, Eimischung einrühren, die Suppe darf nicht mehr kochen! Mit Salz, Pfeffer und Zucker abschmecken.",
                        "leckere Zitronensuppe mit Garnelen", Foodtypes.OMNIVORE);
                path = Uri.parse("android.resource://com.example.kochbuch/"+R.drawable.zitronensuppe);
                zitronensuppe.setPicturePath(path.toString());
                rdao.insert(zitronensuppe);
                long zitronensuppeId = rdao.getRecipeIdByName("Zitronensuppe mit Garnelen");
                recipeIngredients.add(new RecipeIngredient(150,idao.getIngredient("Garnelen")));
                recipeIngredients.add(new RecipeIngredient(150,idao.getIngredient("Möhren")));
                recipeIngredients.add(new RecipeIngredient(150,idao.getIngredient("Zuckerschoten")));
                recipeIngredients.add(new RecipeIngredient(150,idao.getIngredient("Knoblauch")));
                recipeIngredients.add(new RecipeIngredient(150,idao.getIngredient("Gemüsebrühe")));
                recipeIngredients.add(new RecipeIngredient(150,idao.getIngredient("Salz")));
                recipeIngredients.add(new RecipeIngredient(150,idao.getIngredient("Pfeffer")));
                recipeIngredients.add(new RecipeIngredient(150,idao.getIngredient("Langkornreis")));
                recipeIngredients.add(new RecipeIngredient(150,idao.getIngredient("Eier")));
                recipeIngredients.add(new RecipeIngredient(150,idao.getIngredient("Zitronensaft")));
                recipeIngredients.add(new RecipeIngredient(150,idao.getIngredient("Zucker")));
                for (RecipeIngredient ri:recipeIngredients) {
                    ri.setRecipeId(zitronensuppeId);
                }
                riDao.insert(recipeIngredients);
                // clears the ingredients
                recipeIngredients.clear();
                // Hähnchenbrust
                Recipe haehnchen = new Recipe("Überbackene Hähnchenbrust",
                        "Zucchini in 1 cm dicke Scheiben schneiden. Paprikaschoten putzen und in grobe Spalten schneiden. Alles in eine Auflaufform geben, mit Salz, Pfeffer und Oregano würzen und mit Olivenöl beträufeln. Ofen auf 220 Grad (Umluft 200 Grad) vorheizen.\n" +
                                " Mozzarella abtropfen lassen, in 5 Scheiben schneiden und diese halbieren. Hähnchenbrustfilets jeweils in 1cm Abständen fünfmal 1,5–2 cm tief einschneiden. Rundum leicht salzen. Einschnitte mit je 1⁄2 Tl TomatenPesto und 1⁄2 Mozzarella-Scheibe füllen.\n" +
                                " Hähnchenbrustfilets auf das Gemüse setzen. Im heißen Ofen auf einem Rost auf der untersten Schiene 45 Min. überbacken.",
                        "leckere Überbackene Hähnchenbrust", Foodtypes.OMNIVORE);
                path = Uri.parse("android.resource://com.example.kochbuch/"+R.drawable.haehnchen);
                haehnchen.setPicturePath(path.toString());
                rdao.insert(haehnchen);
                long haehnchenId = rdao.getRecipeIdByName("Überbackene Hähnchenbrust");
                recipeIngredients.add(new RecipeIngredient(360,idao.getIngredient("Zucchini")));
                recipeIngredients.add(new RecipeIngredient(120,idao.getIngredient("Paprikagelb")));
                recipeIngredients.add(new RecipeIngredient(120,idao.getIngredient("Paprikarot")));
                recipeIngredients.add(new RecipeIngredient(2,idao.getIngredient("Salz")));
                recipeIngredients.add(new RecipeIngredient(2,idao.getIngredient("Pfeffer")));
                recipeIngredients.add(new RecipeIngredient(10,idao.getIngredient("Oregano")));
                recipeIngredients.add(new RecipeIngredient(10,idao.getIngredient("Olivenöl")));
                recipeIngredients.add(new RecipeIngredient(100,idao.getIngredient("Mozzarella")));
                recipeIngredients.add(new RecipeIngredient(400,idao.getIngredient("Hähnchenbrust")));
                recipeIngredients.add(new RecipeIngredient(70,idao.getIngredient("Tomatenpesto")));
                for (RecipeIngredient ri:recipeIngredients) {
                    ri.setRecipeId(haehnchenId);
                }
                riDao.insert(recipeIngredients);
                // clears the ingredients
                recipeIngredients.clear();
                //steak
                Recipe flanksteak = new Recipe("Flanksteak vom Grill mit Asia-Aromen",
                        "Zitronengras am dicken Ende mit einem Plattiereisen oder einem schwerem Messer faserig klopfen. Ingwer und Knoblauch in dünne Scheiben schneiden. Mit Zitronengras, Sojasauce, Süßesauce und Kaffirlimettenblättern zu einer Marinade verrühren.\n" +
                                " Flanksteak in die Marinade legen und über Nacht abgedeckt im Kühlschrank marinieren.\n" +
                                " Flanksteak aus der Marinade nehmen und mit Küchenpapier trocken tupfen. Auf dem heißen Grill von jeder Seite 4-5 Minuten grillen, anschließend ca. 5 Minuten ruhen lassen.\n" +
                                " Das Steak quer zur Faser in 1 cm dicke Scheiben aufschneiden und mit etwas Pfeffer würzen. Dazu passt Rotkohlsalat mit Kokoschips.",
                        "leckere Flanksteak vom Grill mit Asia-Aromen", Foodtypes.OMNIVORE);
                path = Uri.parse("android.resource://com.example.kochbuch/"+R.drawable.flanksteak);
                flanksteak.setPicturePath(path.toString());
                rdao.insert(flanksteak);
                long flanksteakId = rdao.getRecipeIdByName("Flanksteak vom Grill mit Asia-Aromen");
                recipeIngredients.add(new RecipeIngredient(100,idao.getIngredient("Zitronengras")));
                recipeIngredients.add(new RecipeIngredient(80,idao.getIngredient("Ingwer")));
                recipeIngredients.add(new RecipeIngredient(40,idao.getIngredient("Knoblauch")));
                recipeIngredients.add(new RecipeIngredient(50,idao.getIngredient("Sojasauce")));
                recipeIngredients.add(new RecipeIngredient(150,idao.getIngredient("Süssesauce")));
                recipeIngredients.add(new RecipeIngredient(2,idao.getIngredient("Pfeffer")));
                recipeIngredients.add(new RecipeIngredient(10,idao.getIngredient("Kaffirlimettenblaetter")));
                recipeIngredients.add(new RecipeIngredient(1000,idao.getIngredient("Flanksteak")));
                for (RecipeIngredient ri:recipeIngredients) {
                    ri.setRecipeId(flanksteakId);
                }
                riDao.insert(recipeIngredients);
            });

        }

    };

}
