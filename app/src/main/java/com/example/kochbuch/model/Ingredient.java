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
    private int kcal;



    public String getName(){
        return this.name;
    }
    public void setName(String newName){
        this.name = newName;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public int getKcal() {
        return kcal;
    }

    @NonNull
    @Override
    public String toString() {
        return null;
    }
}
