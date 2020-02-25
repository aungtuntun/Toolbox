package com.imceits.aungtuntun.alephcodeassignment.data.pojos;

import androidx.room.ColumnInfo;
import androidx.room.DatabaseView;

import com.imceits.aungtuntun.alephcodeassignment.data.Status;
import java.util.Date;

@DatabaseView("SELECT tr.*, tl.tool_name, tl.image FROM Tools_rent tr JOIN Tools tl ON tr.tool_id = tl.tool_id ")
public class ToolsRentDto {
    @ColumnInfo(name = "tool_rent_id")
    private int id;
    private int tool_id;
    private int friend_id;
    private Status status;
    private Date created_date;
    private int count;
    @ColumnInfo(name = "tool_name")
    private String name;
    private String image;
    @ColumnInfo(name = "returned_count")
    private int returnedCount;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public int getTool_id() {
        return tool_id;
    }

    public void setTool_id(int tool_id) {
        this.tool_id = tool_id;
    }

    public int getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(int friend_id) {
        this.friend_id = friend_id;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public int getReturnedCount() {
        return returnedCount;
    }

    public void setReturnedCount(int returnedCount) {
        this.returnedCount = returnedCount;
    }

  }
