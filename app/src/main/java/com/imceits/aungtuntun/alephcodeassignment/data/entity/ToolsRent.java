package com.imceits.aungtuntun.alephcodeassignment.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.imceits.aungtuntun.alephcodeassignment.data.Status;

import java.util.Date;

@Entity(tableName = "Tools_rent", indices = {@Index("tool_id"), @Index("friend_id")})
public class ToolsRent {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tool_rent_id")
    private int id;
    @ColumnInfo(name = "tool_id")
    private int toolId;
    @ColumnInfo(name = "friend_id")
    private int friendId;
    @ColumnInfo(name = "status")
    private Status status;
    @ColumnInfo(name = "created_date")
    private Date createdDate;
    @ColumnInfo(name = "returned_count")
    private int returnedCount;

    private int count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getToolId() {
        return toolId;
    }

    public void setToolId(int toolId) {
        this.toolId = toolId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getReturnedCount() {
        return returnedCount;
    }

    public void setReturnedCount(int returnedCount) {
        this.returnedCount = returnedCount;
    }
}
