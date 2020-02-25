package com.imceits.aungtuntun.alephcodeassignment.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Friends")
public class Friends {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "friend_id")
    private int id;
    @ColumnInfo(name = "name")
    private String name;

    public Friends() {
    }

    @Ignore
    public Friends(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
