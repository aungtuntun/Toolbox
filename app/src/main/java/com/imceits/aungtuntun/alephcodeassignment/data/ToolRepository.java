package com.imceits.aungtuntun.alephcodeassignment.data;

import androidx.lifecycle.LiveData;

import com.imceits.aungtuntun.alephcodeassignment.data.entity.Friends;
import com.imceits.aungtuntun.alephcodeassignment.data.entity.ToolsRent;
import com.imceits.aungtuntun.alephcodeassignment.data.pojos.ToolsDto;
import com.imceits.aungtuntun.alephcodeassignment.data.pojos.ToolsRentDto;

import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ToolRepository {
    private final ToolsRentDao toolsRentDao;
    private final ExecutorService executorService;
    private LiveData<List<ToolsDto>> toolsLiveData;
    private LiveData<List<Friends>> friendsLiveData;

    @Inject
    public ToolRepository(ToolsRentDao toolsRentDao, ExecutorService executorService) {
        this.toolsRentDao = toolsRentDao;
        this.executorService = executorService;
        toolsLiveData = toolsRentDao.getAllTools();
        friendsLiveData = toolsRentDao.getAllFriends();
    }


    public void insertToolsRent(ToolsRent toolsRent) {
        executorService.execute(() -> {
            ToolsRent data = toolsRentDao.getExistDataById(toolsRent.getFriendId(), toolsRent.getToolId(), Status.BORROWED);
            if (data != null && data.getId() > 0) {
                toolsRentDao.updateExistBorrowedRecord(data.getId(), toolsRent.getToolId());
            }else {
                toolsRentDao.saveToolsRent(toolsRent);
            }

        });
    }

    public void updateToolsRent(ToolsRent toolsRent) {
        executorService.execute(() -> toolsRentDao.updateReturnedTools(toolsRent.getStatus(), toolsRent.getId()));
    }

    public LiveData<List<ToolsDto>> getAllTools() {
       return toolsLiveData;
    }

    public LiveData<List<Friends>> getAllFriends() {
        return friendsLiveData;
    }

    public LiveData<Friends> getFriendsData(int id) {
        return toolsRentDao.getFriendById(id);
    }

    public LiveData<List<ToolsRentDto>> getToolsRentList(int id) {
        return toolsRentDao.getRentToolsByFriendId(id, Status.BORROWED);
    }


}
