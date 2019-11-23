package com.imceits.aungtuntun.alephcodeassignment.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.imceits.aungtuntun.alephcodeassignment.data.entity.Friends;
import com.imceits.aungtuntun.alephcodeassignment.data.entity.Tools;
import com.imceits.aungtuntun.alephcodeassignment.data.entity.ToolsRent;
import com.imceits.aungtuntun.alephcodeassignment.data.pojos.ToolsDto;
import com.imceits.aungtuntun.alephcodeassignment.data.pojos.ToolsRentDto;

import java.util.List;

@Dao
public interface ToolsRentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(Tools... tools);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertFriends(Friends... friends);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void saveToolsRent(ToolsRent toolsRent);

    @Transaction
    @Query("SELECT * FROM Friends")
    public LiveData<List<Friends>> getAllFriends();

    @Transaction
    @Query("SELECT * FROM ToolsDto")
    public LiveData<List<ToolsDto>> getAllTools();

    @Transaction
    @Query("UPDATE Tools_rent SET status = :status, returned_count = count, count = 0 WHERE tool_rent_id = :toolRentId")
    public abstract void updateReturnedTools(Status status, int toolRentId);

    @Transaction
    @Query("SELECT * FROM Friends WHERE friend_id = :id")
    public abstract LiveData<Friends> getFriendById(int id);

    @Transaction
    @Query("SELECT * FROM ToolsRentDto WHERE status = :status AND friend_id = :id")
    public abstract LiveData<List<ToolsRentDto>> getRentToolsByFriendId(int id, Status status);

    @Transaction
    @Query("SELECT * FROM Tools_rent WHERE friend_id = :friendId AND tool_id = :toolId AND status = :status")
    public abstract ToolsRent getExistDataById(int friendId, int toolId, Status status);

    @Transaction
    @Query("UPDATE Tools_rent SET count = count + 1 WHERE tool_rent_id = :rentId AND tool_id = :toolId")
    public abstract void updateExistBorrowedRecord(int rentId, int toolId);
}
