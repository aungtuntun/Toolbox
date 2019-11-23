package com.imceits.aungtuntun.alephcodeassignment.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.imceits.aungtuntun.alephcodeassignment.data.ToolRepository;
import com.imceits.aungtuntun.alephcodeassignment.data.entity.Friends;
import com.imceits.aungtuntun.alephcodeassignment.data.entity.ToolsRent;
import com.imceits.aungtuntun.alephcodeassignment.data.pojos.ToolsRentDto;
import com.imceits.aungtuntun.alephcodeassignment.utils.AbsentLiveData;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class DetailViewModel extends ViewModel {

    private final ToolRepository toolRepository;
    private final LiveData<Friends> friendsLiveData;
    private final MutableLiveData<Integer> idLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> friendIdLiveData = new MutableLiveData<>();
    private final LiveData<List<ToolsRentDto>> toolsRentLiveData;
    @Inject
    DetailViewModel(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
        friendsLiveData = Transformations.switchMap(idLiveData, input -> {
            if (input == 0) {
                return AbsentLiveData.create();
            }else {
                return toolRepository.getFriendsData(input);
            }
        });
        toolsRentLiveData = Transformations.switchMap(friendIdLiveData, input -> {
            if (input == 0) {
                return AbsentLiveData.create();
            } else {
                return toolRepository.getToolsRentList(input);
            }
        });
    }

    public LiveData<Friends> getFriendsLiveData() {
        return friendsLiveData;
    }

    public LiveData<List<ToolsRentDto>> getToolsRentLiveData() {
        return toolsRentLiveData;
    }

    void updateToolsRent(ToolsRent toolsRent) {
        toolRepository.updateToolsRent(toolsRent);
    }

    void updateId(int id) {
        if (Objects.equals(id, idLiveData.getValue()))
            return;
        idLiveData.setValue(id);
        friendIdLiveData.setValue(id);
    }
}
