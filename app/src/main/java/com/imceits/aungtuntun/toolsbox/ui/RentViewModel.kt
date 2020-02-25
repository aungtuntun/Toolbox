package com.imceits.aungtuntun.toolsbox.ui

import android.app.Application
import androidx.lifecycle.*
import com.imceits.aungtuntun.toolsbox.data.ToolDatabase
import com.imceits.aungtuntun.toolsbox.data.ToolRepository
import com.imceits.aungtuntun.toolsbox.data.entity.Friends
import com.imceits.aungtuntun.toolsbox.data.entity.ToolsRent
import com.imceits.aungtuntun.toolsbox.data.views.ToolsRentDto
import com.imceits.aungtuntun.toolsbox.utils.AbsentLiveData
import kotlinx.coroutines.launch
import java.util.*

class RentViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ToolRepository
    val toolsRentLiveData: LiveData<List<ToolsRentDto>>
    val friendLiveData : LiveData<Friends>
    private val idLiveData: MutableLiveData<Int> = MutableLiveData()
    private val friendIdLiveData: MutableLiveData<Int> = MutableLiveData()

    init {
        val toolsRentDao = ToolDatabase.getInstance(application, viewModelScope).toolsRentDao()
        repository = ToolRepository(toolsRentDao)
        toolsRentLiveData = Transformations.switchMap(friendIdLiveData) {
            if (it == 0) {
                AbsentLiveData.create()
            }else {
                repository.getToolsRentList(it)
            }
        }

        friendLiveData = Transformations.switchMap(idLiveData) {
            if (it == 0) {
                AbsentLiveData.create()
            }else{
                repository.getFriendsData(it)
            }
        }
    }

    fun updateToolsRent(toolsRent: ToolsRent) = viewModelScope.launch {
        repository.updateToolsRent(toolsRent)
    }

    fun updateId(id: Int) {
        if (Objects.equals(id, idLiveData.value))
            return
        idLiveData.value = id
        friendIdLiveData.value = id
    }
}
