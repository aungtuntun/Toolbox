package com.imceits.aungtuntun.alephcodeassignment.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.imceits.aungtuntun.alephcodeassignment.data.ToolRepository;
import com.imceits.aungtuntun.alephcodeassignment.data.entity.Friends;
import com.imceits.aungtuntun.alephcodeassignment.data.entity.ToolsRent;
import com.imceits.aungtuntun.alephcodeassignment.data.pojos.ToolsDto;

import java.util.List;

import javax.inject.Inject;

public class ToolViewModel extends ViewModel {

    private final ToolRepository toolRepository;

    private LiveData<List<ToolsDto>> toolsList;
    private LiveData<List<Friends>> friendsList;

    @Inject
    ToolViewModel(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
        toolsList = toolRepository.getAllTools();
        friendsList = toolRepository.getAllFriends();
    }

    public LiveData<List<ToolsDto>> getToolsList() {
        return toolsList;
    }

    LiveData<List<Friends>> getFriendsList() {
        return friendsList;
    }

    void insertToolsRent(ToolsRent toolsRent) {
        toolRepository.insertToolsRent(toolsRent);
    }

}
