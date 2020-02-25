package com.imceits.aungtuntun.toolsbox.data

import androidx.lifecycle.LiveData
import com.imceits.aungtuntun.toolsbox.data.entity.Friends
import com.imceits.aungtuntun.toolsbox.data.entity.ToolsRent
import com.imceits.aungtuntun.toolsbox.data.views.ToolsDto
import com.imceits.aungtuntun.toolsbox.data.views.ToolsRentDto

class ToolRepository(private val toolsRentDao: ToolsRentDao) {

    val toolsList: LiveData<List<ToolsDto>> = toolsRentDao.getAllTools()
    val friendList = toolsRentDao.getAllFriends()

    suspend fun insertToolsRent(toolsRent: ToolsRent) {
        val data = toolsRentDao.getExistDataById(toolsRent.friendId, toolsRent.toolId, Status.BORROWED)
        data?.let {
            toolsRentDao.updateExistBorrowedRecord(data.id, toolsRent.toolId)
        }?: toolsRentDao.saveToolsRent(toolsRent)
        /*if (data?.id!! > 0){
            toolsRentDao.updateExistBorrowedRecord(data.id, toolsRent.toolId)
        }else{
            toolsRentDao.saveToolsRent(toolsRent)
        }*/
    }

    suspend fun updateToolsRent(toolsRent: ToolsRent) {
        toolsRentDao.updateReturnedTools(toolsRent.status, toolsRent.id)
    }

    fun getFriendsData(id: Int) : LiveData<Friends> {
        return toolsRentDao.getFriendById(id)
    }

    fun getToolsRentList(id: Int): LiveData<List<ToolsRentDto>>? {
        return toolsRentDao.getRentToolsByFriendId(id, Status.BORROWED)
    }

}