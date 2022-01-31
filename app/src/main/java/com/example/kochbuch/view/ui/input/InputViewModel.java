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

        //Ingredienten:
        //Obst:
        Ingredient apfel = new Ingredient("Apfel",52);
        Ingredient ananas = new Ingredient("Ananas",55);
        Ingredient aprikose = new Ingredient("Aprikose",43);
        Ingredient birne = new Ingredient("Birne",55);
        Ingredient banane = new Ingredient("Banane",88);
        Ingredient blaubeeren = new Ingredient("Blaubeeren",35);
        Ingredient blutorange = new Ingredient("Blutorange",45);
        Ingredient brombeeren = new Ingredient("Brombeeren",43);
        Ingredient cranberries = new Ingredient("Cranberries",46);
        Ingredient erdbeeren = new Ingredient("Erdbeeren",32);
        Ingredient feige = new Ingredient("Feige",107);
        Ingredient grapefruit = new Ingredient("Grapefruit",50);
        Ingredient granatapfel = new Ingredient("Granatapfel",74);
        Ingredient hagebutte = new Ingredient("Hagebutte",162);
        Ingredient honigmelone = new Ingredient("Honigmelone",54);
        Ingredient himbeeren = new Ingredient("Himbeeren",35);
        Ingredient ingwer = new Ingredient("Ingwer",80);
        Ingredient kiwi = new Ingredient("Kiwi",51);
        Ingredient kirschen = new Ingredient("Kirschen",50);
        Ingredient litschi = new Ingredient("Litschi",66);
        Ingredient mandarine = new Ingredient("Mandarine",50);
        Ingredient mango = new Ingredient("mango",62);
        Ingredient maracuja = new Ingredient("Maracuja",97);
        Ingredient pflaume = new Ingredient("Pflaume",47);
        Ingredient pfirsich = new Ingredient("Pfirsich",41);
        Ingredient quitte = new Ingredient("Quitte",38);
        Ingredient rhabarber = new Ingredient("Rhabarber",21);
        Ingredient wassermelone = new Ingredient("Wassermelone",30);
        Ingredient weintraube = new Ingredient("Weintraube",70);
        Ingredient zitrone = new Ingredient("Zitrone",35);

        //Gemüse
        Ingredient aubergine = new Ingredient("Aubergine",24);
        Ingredient artischocke = new Ingredient("Artischocke",47);
        Ingredient avocado = new Ingredient("Avocado",160);
        Ingredient blumenkohl = new Ingredient("Blumenkohl",25);
        Ingredient brokkoli = new Ingredient("Brokkoli",35);
        Ingredient bohnen = new Ingredient("Bohnen",25);
        Ingredient brunnenkresse = new Ingredient("Brunnenkresse",19);
        Ingredient champignons = new Ingredient("Champignons",22);
        Ingredient chinakohl = new Ingredient("Chinakohl",13);
        Ingredient chili = new Ingredient("Chili",40);
        Ingredient erbsen = new Ingredient("Erbsen",82);
        Ingredient eisbergsalat = new Ingredient("Eisbergsalat",14);
        Ingredient fenchel = new Ingredient("Fenchel",31);
        Ingredient gurke = new Ingredient("Gurke",15);
        Ingredient gruenkohl = new Ingredient("Grünkohl",49);
        Ingredient karotte = new Ingredient("Karotte",36);
        Ingredient moehren = new Ingredient("Möhren",36);
        Ingredient kartoffel = new Ingredient("Kartoffel",86);
        Ingredient kohlrabi = new Ingredient("Kohlrabi",27);
        Ingredient kuerbis = new Ingredient("Kürbis",19);
        Ingredient lauch = new Ingredient("Lauch",31);
        Ingredient mais = new Ingredient("Mais",108);
        Ingredient mangold = new Ingredient("Mangold",19);
        Ingredient paprikagruen = new Ingredient("Paprikagrün",17);
        Ingredient paprikagelb = new Ingredient("Paprikagelb",28);
        Ingredient paprikarot = new Ingredient("Paprikarot",31);
        Ingredient radieschen = new Ingredient("Radieschen",16);
        Ingredient rotebete = new Ingredient("Rote Bete",43);
        Ingredient rotkohl = new Ingredient("Rotkohl",29);
        Ingredient rosenkohl = new Ingredient("Rosenkohl",43);
        Ingredient rucola = new Ingredient("Rucola",25);
        Ingredient spargel = new Ingredient("Spargel",18);
        Ingredient spinat = new Ingredient("Spinat",23);
        Ingredient suesskartoffel = new Ingredient("Süßkartoffel",86);
        Ingredient zucchini = new Ingredient("Zucchini",20);
        Ingredient zuckerschoten = new Ingredient("Zuckerschoten",58);
        Ingredient zwiebel = new Ingredient("Zwiebel",40);


        //Fleisch
        Ingredient bratwurst = new Ingredient("Bratwurst",375);
        Ingredient ente = new Ingredient("Ente",375);
        Ingredient flanksteak = new Ingredient("Flanksteak",192);
        Ingredient hirsch = new Ingredient("Hirsch",375);
        Ingredient haehnchenbrust = new Ingredient("Hähnchenbrust",75);
        Ingredient kalbfleisch = new Ingredient("Kalbfleisch",94);
        Ingredient lamm = new Ingredient("Lamm",178);
        Ingredient putenbrust = new Ingredient("Putenbrust",111);
        Ingredient salami = new Ingredient("Salami",507);
        Ingredient schinken = new Ingredient("Schinken",335);
        Ingredient speck = new Ingredient("Speck",645);
        Ingredient rinderfilet = new Ingredient("Rinderfilet",115);
        Ingredient rinderhack = new Ingredient("Rinderhack",212);
        Ingredient rumpsteak = new Ingredient("Rumpsteak",162);
        Ingredient schweinefilet = new Ingredient("Schweinefilet",171);
        Ingredient schweinefleischfett = new Ingredient("Schweinefleisch, fett",311);
        Ingredient schweinefleischmager  = new Ingredient("Schweinefleisch, mager ",143);
        Ingredient schweineschnitzel = new Ingredient("Schweineschnitzel",105);
        Ingredient wienerwuerstchen = new Ingredient("Wiener Würstchen",375);

        //Fisch
        Ingredient forelle = new Ingredient("Forelle",50);
        Ingredient garnelen = new Ingredient("Garnelen",99);
        Ingredient hecht = new Ingredient("Hecht",50);
        Ingredient hering = new Ingredient("Hering",146);
        Ingredient lachs = new Ingredient("Lachs",137);
        Ingredient rotbarschfilet = new Ingredient("Rotbarschfilet",111);
        Ingredient seelachsfilet = new Ingredient("Seelachsfilet",83);
        Ingredient thunfisch = new Ingredient("Thunfisch",144);

        //Milchprodukte & Ei
        Ingredient buttermilch = new Ingredient("Buttermilch",28);
        Ingredient cremefraiche = new Ingredient("Crème fraîche",292);
        Ingredient cheddar = new Ingredient("Cheddar",403);
        Ingredient emmentaler = new Ingredient("Emmentaler",382);
        Ingredient edamer = new Ingredient("Edamer",251);
        Ingredient eier = new Ingredient("Eier ",155);
        Ingredient huettenkaese = new Ingredient("Hüttenkäse",104);
        Ingredient kokosmilch = new Ingredient("Kokosmilch",136);
        Ingredient milch = new Ingredient("Milch",47);
        Ingredient magerquark = new Ingredient("Magerquark",67);
        Ingredient mozzarella = new Ingredient("Mozzarella",280);
        Ingredient naturjoghurt = new Ingredient("Naturjoghurt",62);
        Ingredient parmesan = new Ingredient("Parmesan",431);
        Ingredient sahne = new Ingredient("Sahne",204);
        Ingredient sauerrahm = new Ingredient("Sauerrahm",162);
        Ingredient sauresahne = new Ingredient("Saure Sahne",115);
        Ingredient schmand = new Ingredient("Schmand",240);


        //Nudeln & reis
        Ingredient eiernudeln = new Ingredient("Eiernudeln",369);
        Ingredient spaetzle = new Ingredient("Spätzle",352);
        Ingredient spaghetti = new Ingredient("Spaghetti",350);
        Ingredient reisnudeln = new Ingredient("Reisnudeln",360);
        Ingredient vollkornnudeln = new Ingredient("Vollkornnudeln",323);
        Ingredient sojanudeln = new Ingredient("Sojanudeln",387);
        Ingredient bandnudeln = new Ingredient("Bandnudeln",369);
        Ingredient penne = new Ingredient("Penne",350);
        Ingredient langkornreis = new Ingredient("Langkornreis",277);


        //Gewürze

        Ingredient ajvar = new Ingredient("Ajvar",86);
        Ingredient anis = new Ingredient("Anis",337);
        Ingredient basilikum = new Ingredient("Basilikum",23);
        Ingredient beifuss = new Ingredient("Beifuß",42);
        Ingredient bockshornklee = new Ingredient("Bockshornklee",323);
        Ingredient bohnenkraut = new Ingredient("Bohnenkraut",272);
        Ingredient borretsch = new Ingredient("Borretsch",21);
        Ingredient baerlauch = new Ingredient("Bärlauch",77);
        Ingredient cayennepfeffer = new Ingredient("Cayenne-Pfeffer",299);
        Ingredient peperoni = new Ingredient("Peperoni",314);
        Ingredient curry = new Ingredient("Curry",325);
        Ingredient dill = new Ingredient("Dill",43);
        Ingredient estragon = new Ingredient("Estragon",295);
        Ingredient jalapeno = new Ingredient("Jalapeno",30);
        Ingredient kakao = new Ingredient("Kakao",433);
        Ingredient kaffirlimettenblaetter = new Ingredient("Kaffirlimettenblaetter",67);
        Ingredient kapern = new Ingredient("Kapern",416);
        Ingredient kardamon = new Ingredient("Kardamon",311);
        Ingredient knoblauch = new Ingredient("Knoblauch",149);
        Ingredient kuemmel = new Ingredient("Kümmel",311);
        Ingredient majoran = new Ingredient("Majoran",371);
        Ingredient minze = new Ingredient("Minze",285);
        Ingredient muskatnuss = new Ingredient("Muskatnuss",525);
        Ingredient oregano = new Ingredient("Oregano",306);
        Ingredient pfeffer = new Ingredient("Pfeffer",255);
        Ingredient salz = new Ingredient("Salz",0);
        Ingredient thymian = new Ingredient("Thymian",101);
        Ingredient trueffel = new Ingredient("Trüffel",48);
        Ingredient zimt = new Ingredient("Zimt",248);
        Ingredient zitronengras = new Ingredient("Zitronengras",99);
        Ingredient zitronensaft = new Ingredient("Zitronensaft",29);
        Ingredient zucker = new Ingredient("Zucker",409);

        //Sosse

        Ingredient tomatensosse = new Ingredient("Tomatensosse",29);
        Ingredient gemuesebruehe = new Ingredient("gemuesebruehe",3);
        Ingredient tomatenpesto = new Ingredient("Tomatenpesto",426);
        Ingredient sojasauce = new Ingredient("Sojasauce",53);
        Ingredient suessesauce = new Ingredient("suessesauce",53);

        //Oil
        Ingredient olivenoel = new Ingredient("Olivenöl",884);
        Ingredient sonnenblumenoel = new Ingredient("Sonnenblumenöl",884);


        Recipe recipe = new Recipe("Spagetti mit Tomatensoße",
                "In Kochtopf mit 1L Wasser werfen. Herd auf volle Pulle stellen und warten bis es Kocht.Die Nudel für 8min Kochen.Herd aus Stellen wasser aus Kochtopf abgießen. Tomatensoße und Käse drauf. Fertig!",
                "leckere Nudeln mit Tomatensoße", Foodtypes.VEGETARIAN);

        RecipeIngredient spagettiZ = new RecipeIngredient(150,spaghetti);
        RecipeIngredient tomatensosseZ = new RecipeIngredient(100,tomatensosse);
        RecipeIngredient kaeseZ = new RecipeIngredient(40,parmesan);

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

        RecipeIngredient garnelenZ = new RecipeIngredient(150,garnelen);
        RecipeIngredient moehrenZ = new RecipeIngredient(200,moehren);
        RecipeIngredient zuckerschotenZ = new RecipeIngredient(100,zuckerschoten);
        RecipeIngredient knoblauchZ = new RecipeIngredient(10,knoblauch);
        RecipeIngredient gemuesebrueheZ = new RecipeIngredient(800,gemuesebruehe);
        RecipeIngredient salzZ = new RecipeIngredient(2,salz);
        RecipeIngredient pfefferZ = new RecipeIngredient(2,pfeffer);
        RecipeIngredient langkornreisZ = new RecipeIngredient(70,langkornreis);
        RecipeIngredient eierZ = new RecipeIngredient(100,eier);
        RecipeIngredient zitronensaftZ = new RecipeIngredient(45,zitronensaft);
        RecipeIngredient zuckerZ = new RecipeIngredient(2,zucker);


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

        RecipeIngredient zucchiniZ = new RecipeIngredient(360,zucchini);
        RecipeIngredient paprikagelbZ = new RecipeIngredient(120,paprikagelb);
        RecipeIngredient paprikarotZ = new RecipeIngredient(120,paprikarot);
        RecipeIngredient salzZ2 = new RecipeIngredient(2,salz);
        RecipeIngredient pfefferZ2 = new RecipeIngredient(2,pfeffer);
        RecipeIngredient oreganoZ = new RecipeIngredient(10,oregano);
        RecipeIngredient olivenoelZ = new RecipeIngredient(10,olivenoel);
        RecipeIngredient mozzarellaZ = new RecipeIngredient(100,mozzarella);
        RecipeIngredient haehnchenbrustfiletsZ = new RecipeIngredient(400,haehnchenbrust);
        RecipeIngredient tomatenpestoZ = new RecipeIngredient(70,tomatenpesto);


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

        RecipeIngredient zitronengrasZ = new RecipeIngredient(100,zitronengras);
        RecipeIngredient ingwerZ = new RecipeIngredient(80,ingwer);
        RecipeIngredient knoblauchZ3 = new RecipeIngredient(40,knoblauch);
        RecipeIngredient sojasauceZ = new RecipeIngredient(50,sojasauce);
        RecipeIngredient suessesauceZ = new RecipeIngredient(150,suessesauce);
        RecipeIngredient pfefferZ3 = new RecipeIngredient(2,pfeffer);
        RecipeIngredient kaffirlimettenblaetterZ = new RecipeIngredient(10,kaffirlimettenblaetter);
        RecipeIngredient flanksteakZ = new RecipeIngredient(1000,flanksteak);



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
