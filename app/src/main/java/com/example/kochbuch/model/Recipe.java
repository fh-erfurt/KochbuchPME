package com.example.kochbuch.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.example.kochbuch.enums.Foodtypes;

import java.util.List;
@Entity(tableName = "Recipe")
public class Recipe extends Basemodel {

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "instruction")
    private String instruction;

    @NonNull
    @ColumnInfo(name = "description")
    private String description;

    public Recipe(String name,String instruction,String description){
        this.name = name;
        this.instruction = instruction;
        this.description = description;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }


    @NonNull
    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(@NonNull String instruction) {
        this.instruction = instruction;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }


    @NonNull
    @Override
    public String toString() {
        return null;
    }


}
