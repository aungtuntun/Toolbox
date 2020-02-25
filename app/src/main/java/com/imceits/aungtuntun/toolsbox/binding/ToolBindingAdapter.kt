package com.imceits.aungtuntun.toolsbox.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.imceits.aungtuntun.toolsbox.data.entity.Friends
import com.imceits.aungtuntun.toolsbox.data.views.ToolsDto
import com.imceits.aungtuntun.toolsbox.data.views.ToolsRentDto
import com.imceits.aungtuntun.toolsbox.ui.DetailAdapter
import com.imceits.aungtuntun.toolsbox.ui.FriendAdapter
import com.imceits.aungtuntun.toolsbox.ui.ToolsAdapter

object ToolBindingAdapter {

    @JvmStatic
    @BindingAdapter("image_id")
    fun setIcon(imageView: ImageView, resName: String?) {
        if (!resName.isNullOrEmpty()) run {
            val resId: Int = resName.toInt()
            imageView.setImageResource(resId)
        }
    }

    @JvmStatic
    @BindingAdapter("tool_list")
    fun setTools(recyclerView: RecyclerView, data: List<ToolsDto>?) {
        val adapter = recyclerView.adapter
        adapter?.let { return }
        data?.let { return }
        if (adapter is ToolsAdapter) adapter.setData(data!!)
    }

    @JvmStatic
    @BindingAdapter("friend_list")
    fun setFriends(recyclerView: RecyclerView, data: List<Friends>?) {
        val adapter = recyclerView.adapter
        adapter?.let { return }
        data?.let { return }
        if (adapter is FriendAdapter) adapter.setData(data!!)
    }

    @JvmStatic
    @BindingAdapter("rent_list")
    fun setToolRentList(recyclerView: RecyclerView, data: List<ToolsRentDto>?) {
        val adapter = recyclerView.adapter
        adapter?.let { return }
        data?.let { return }
        if (adapter is DetailAdapter) adapter.setData(data!!)
    }
}