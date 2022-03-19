package com.example.kochbuch.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * the Basemodel for model classes (all models share these columns)
 */
public abstract class Basemodel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id = 0;

    @NonNull
    @ColumnInfo(name = "created")
    private long created = Long.MIN_VALUE;

    @NonNull
    @ColumnInfo(name = "modified")
    private long modified;

    @NonNull
    @ColumnInfo(name = "version")
    private int version = 0;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public long getCreated() {
        return created;
    }
    public void setCreated(long created) {
        this.created = created;
    }
    public long getModified() {
        return modified;
    }
    public void setModified(long modified) {
        this.modified = modified;
    }
    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }

    @NonNull
    @Override
    public abstract String toString();

}
