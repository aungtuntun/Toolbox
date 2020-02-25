package com.imceits.aungtuntun.toolsbox.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.imceits.aungtuntun.toolsbox.data.entity.Friends
import com.imceits.aungtuntun.toolsbox.data.entity.Tools
import com.imceits.aungtuntun.toolsbox.data.entity.ToolsRent
import com.imceits.aungtuntun.toolsbox.data.views.ToolsDto
import com.imceits.aungtuntun.toolsbox.data.views.ToolsRentDto

@Dao
interface ToolsRentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg tools: Tools)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<Tools>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFriends(data: List<Friends>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFriends(vararg friends: Friends)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveToolsRent(toolsRent: ToolsRent)

    @Transaction
    @Query("SELECT * FROM Friends")
    fun getAllFriends(): LiveData<List<Friends>>

    @Transaction
    @Query("SELECT * FROM ToolsDto")
    fun getAllTools(): LiveData<List<ToolsDto>>

    @Transaction
    @Query("SELECT * FROM Friends WHERE friend_id = :id")
    fun getFriendById(id: Int) : LiveData<Friends>

    @Transaction
    @Query("SELECT * FROM ToolsRentDto WHERE status = :status AND friend_id = :id")
    fun getRentToolsByFriendId(id: Int, status: Status) : LiveData<List<ToolsRentDto>>?
    @Transaction
    @Query("SELECT * FROM Tools_rent WHERE friend_id = :id AND tool_id = :toolId AND status = :status")
    suspend fun getExistDataById(id: Int, toolId: Int, status: Status): ToolsRent?

    @Transaction
    @Query("UPDATE Tools_rent SET status = :status, returned_count = count, count = 0 WHERE tool_rent_id = :toolRentId")
    suspend fun updateReturnedTools(status: Status, toolRentId: Int)

    @Transaction
    @Query("UPDATE Tools_rent SET count = count + 1 WHERE tool_rent_id = :rentId AND tool_id = :toolId")
    suspend fun updateExistBorrowedRecord(rentId: Int, toolId: Int)

}