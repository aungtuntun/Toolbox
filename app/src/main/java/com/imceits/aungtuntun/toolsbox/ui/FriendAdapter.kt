package com.imceits.aungtuntun.toolsbox.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.imceits.aungtuntun.toolsbox.data.entity.Friends
import com.imceits.aungtuntun.toolsbox.databinding.FriendItemBinding
import com.imceits.aungtuntun.toolsbox.utils.BaseAdapter

class FriendAdapter : BaseAdapter<FriendAdapter.FriendViewHolder, Friends>() {
    private var dataList: List<Friends> = emptyList()

    override fun setData(data: List<Friends>) {
        dataList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val itemBinding: FriendItemBinding = FriendItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.onBind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    class FriendViewHolder(itemBinding1: FriendItemBinding) : RecyclerView.ViewHolder(itemBinding1.root) {
        private val itemBinding = itemBinding1

        fun onBind(friends: Friends) {
            itemBinding.data = friends
            itemBinding.executePendingBindings()
            val directions: FriendFragmentDirections.ActionFriendToRent = FriendFragmentDirections.actionFriendToRent()
            directions.friendId = friends.id
            itemBinding.cardFriend.setOnClickListener(Navigation.createNavigateOnClickListener(directions))
        }
    }
}