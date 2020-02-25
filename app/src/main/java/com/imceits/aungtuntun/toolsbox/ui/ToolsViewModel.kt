package com.imceits.aungtuntun.toolsbox.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.imceits.aungtuntun.toolsbox.data.ToolDatabase
import com.imceits.aungtuntun.toolsbox.data.ToolRepository
import com.imceits.aungtuntun.toolsbox.data.entity.Friends
import com.imceits.aungtuntun.toolsbox.data.entity.ToolsRent
import com.imceits.aungtuntun.toolsbox.data.views.ToolsDto
import kotlinx.coroutines.launch


class ToolsViewModel(application: Application) : AndroidViewModel(application) {
private val repository: ToolRepository
    val toolsList: LiveData<List<ToolsDto>>
    val friendList: LiveData<List<Friends>>

    init {
        val toolsRentDao = ToolDatabase.getInstance(application, viewModelScope).toolsRentDao()
        repository = ToolRepository(toolsRentDao)
        toolsList = repository.toolsList
        friendList = repository.friendList
    }

    fun insertToolsRent(toolsRent: ToolsRent) = viewModelScope.launch   {
        repository.insertToolsRent(toolsRent)
    }
}
