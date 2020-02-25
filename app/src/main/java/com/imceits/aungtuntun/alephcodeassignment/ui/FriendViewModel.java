package com.imceits.aungtuntun.alephcodeassignment.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.imceits.aungtuntun.alephcodeassignment.data.ToolRepository;
import com.imceits.aungtuntun.alephcodeassignment.data.entity.Friends;

import java.util.List;

import javax.inject.Inject;

public class FriendViewModel extends ViewModel {

    private final ToolRepository toolRepository;
    private LiveData<List<Friends>> friendsList;

    @Inject
    FriendViewModel(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
        friendsList = this.toolRepository.getAllFriends();
    }

    public LiveData<List<Friends>> getFriendsList() {
        return friendsList;
    }
}
