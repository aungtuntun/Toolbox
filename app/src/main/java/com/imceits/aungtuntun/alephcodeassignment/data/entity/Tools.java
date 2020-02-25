package com.imceits.aungtuntun.alephcodeassignment.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Tools")
public class Tools {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tool_id")
    private int id;
    @ColumnInfo(name = "tool_name")
    private String name;
    @ColumnInfo(name = "image")
    private String image;
    @ColumnInfo(name = "item_count")
    private int count;

    public Tools() {
    }

    @Ignore
    public Tools(String name, String image, int count) {
        this.name = name;
        this.image = image;
        this.count = count;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
