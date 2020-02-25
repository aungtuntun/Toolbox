package com.imceits.aungtuntun.toolsbox.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.imceits.aungtuntun.toolsbox.R
import com.imceits.aungtuntun.toolsbox.data.entity.Friends
import com.imceits.aungtuntun.toolsbox.data.views.ToolsDto
import com.imceits.aungtuntun.toolsbox.databinding.ToolItemBinding
import com.imceits.aungtuntun.toolsbox.utils.BaseAdapter
import java.util.*

class ToolsAdapter(private val mContext: Context, private val listener: BorrowListener) : BaseAdapter<ToolsAdapter.ToolViewHolder, ToolsDto>() {
    private var dataList: List<ToolsDto> = emptyList()
    private var friendList: List<Friends> = emptyList()
    private val nameList: MutableList<String> = mutableListOf()

    override fun setData(data: List<ToolsDto>) {
        dataList = data
        notifyDataSetChanged()
    }

    fun setFriendList(friends: List<Friends>) {
        friendList = friends
        if (!friends.isNullOrEmpty()) {
            for (fri in friends) {
                nameList.add(fri.name)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToolViewHolder {
        val itemBinding: ToolItemBinding = ToolItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToolViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ToolViewHolder, position: Int) {
        holder.onBind(dataList[position])
        holder.itemBinding.cardTool.setOnClickListener {
            createDialog(dataList[position])
        }
    }

    class ToolViewHolder(toolItemBinding: ToolItemBinding) : RecyclerView.ViewHolder(toolItemBinding.root) {
        val itemBinding: ToolItemBinding = toolItemBinding

        fun onBind(tool: ToolsDto) {
            itemBinding.item = tool
            itemBinding.executePendingBindings()
        }
    }


    @SuppressLint("InflateParams")
    private fun createDialog(tool: ToolsDto) {
        if (tool.count > tool.rentCount){
            val builder: AlertDialog.Builder = AlertDialog.Builder(mContext)
            builder.setTitle(mContext.getString(R.string.title_name))
            val inflater: LayoutInflater = LayoutInflater.from(mContext)
            val dialogView: View = inflater.inflate(R.layout.search_friend, null)
            val autoView: AutoCompleteTextView = dialogView.findViewById(R.id.auto_name)
            autoView.threshold = 1
            val adapter: ArrayAdapter<String> = ArrayAdapter(mContext, android.R.layout.select_dialog_item, nameList)
            autoView.setAdapter(adapter)
            builder.setView(dialogView)
            builder.setPositiveButton("OK") { dialog, which ->
                val name: String = autoView.text.toString()
                val friend: Friends? = getFriend(name)
                listener.borrowed(tool, friend)
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }else {
            Toast.makeText(mContext, "Tool is nothing left to borrow!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getFriend(name: String): Friends? {
        for (fri in friendList) {
            if (Objects.equals(name, fri.name)){
                return fri
            }
        }
        return null
    }
    interface BorrowListener {
        fun borrowed(tool: ToolsDto, friend: Friends?)
    }
}