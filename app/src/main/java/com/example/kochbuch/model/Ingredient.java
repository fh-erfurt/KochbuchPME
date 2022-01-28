package com.example.kochbuch.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.example.kochbuch.enums.Foodtypes;

@Entity(tableName = "Ingredient")
public class Ingredient extends Basemodel {



    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "kcal100")
    private int kcal100;

    public Ingredient(String name,int kcal100){
        this.name = name;
        this.kcal100 = kcal100;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String newName){
        this.name = newName;
    }

    public void setKcal100(int kcal100) {
        this.kcal100 = kcal100;
    }

    public int getKcal100() {
        return kcal100;
    }

    @NonNull
    @Override
    public String toString() {
        return null;
    }
}
