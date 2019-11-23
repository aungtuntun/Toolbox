package com.imceits.aungtuntun.alephcodeassignment.data.pojos;


import androidx.room.ColumnInfo;
import androidx.room.DatabaseView;

@DatabaseView("SELECT DISTINCT tl.tool_id, tl.tool_name, tl.image, tl.item_count, IFNULL(SUM(tr.count), 0) AS rent_count, " +
        " IFNULL(SUM(tr.returned_count), 0) AS return_count FROM Tools tl LEFT JOIN Tools_rent tr ON tl.tool_id = tr.tool_id " +
        " GROUP BY tl.tool_id, tl.tool_name, tl.image, tl.item_count;")
public class ToolsDto {

    @ColumnInfo(name = "tool_id")
    private int id;
    @ColumnInfo(name = "tool_name")
    private String name;
    @ColumnInfo(name = "image")
    private String image;
    @ColumnInfo(name = "item_count")
    private int count;
    @ColumnInfo(name = "rent_count")
    private int rentCount;

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

    public int getRentCount() {
        return rentCount;
    }

    public void setRentCount(int rentCount) {
        this.rentCount = rentCount;
    }
}
