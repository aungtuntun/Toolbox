package com.imceits.aungtuntun.toolsbox.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.imceits.aungtuntun.toolsbox.data.ToolDatabase
import com.imceits.aungtuntun.toolsbox.data.ToolRepository
import com.imceits.aungtuntun.toolsbox.data.entity.Friends

class FriendViewModel(application: Application) : AndroidViewModel(application) {

    val friendsList: LiveData<List<Friends>>
    private val repository: ToolRepository

    init {
        val toolsRentDao = ToolDatabase.getInstance(application, viewModelScope).toolsRentDao()
        repository = ToolRepository( toolsRentDao)
        friendsList = repository.friendList
    }

}
