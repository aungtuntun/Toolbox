package com.imceits.aungtuntun.toolsbox.ui

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imceits.aungtuntun.toolsbox.R
import com.imceits.aungtuntun.toolsbox.data.views.ToolsRentDto
import com.imceits.aungtuntun.toolsbox.databinding.RentItemBinding
import com.imceits.aungtuntun.toolsbox.utils.BaseAdapter

class DetailAdapter(private val mContext: Context, private val listener: ReturnedListener): BaseAdapter<DetailAdapter.DetailViewHolder, ToolsRentDto> () {
    private var dataList: MutableList<ToolsRentDto> = mutableListOf()

    override fun setData(data: List<ToolsRentDto>) {
        dataList = data as MutableList<ToolsRentDto>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val itemBinding: RentItemBinding = RentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.onBind(dataList[position])
        holder.itemBinding.cardTool.setOnClickListener {
            createDialog(dataList[position], position)
        }
    }

    class DetailViewHolder(rentItemBinding: RentItemBinding) : RecyclerView.ViewHolder(rentItemBinding.root) {
        val itemBinding: RentItemBinding = rentItemBinding

        fun onBind(toolsRentDto: ToolsRentDto) {
            itemBinding.data = toolsRentDto
            itemBinding.executePendingBindings()
        }
    }

    private fun createDialog(toolsRentDto: ToolsRentDto, position: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(mContext)
        builder.setTitle(mContext.getString(R.string.return_tool))
        builder.setMessage(mContext.getString(R.string.return_message))
        builder.setNegativeButton(mContext.getString(R.string.button_cancel)
        ) { dialog, which -> dialog.dismiss()  }
        builder.setPositiveButton(mContext.getString(R.string.button_ok)
        ) { dialog, which ->
            listener.returned(toolsRentDto)
            dataList.remove(toolsRentDto)
            notifyItemRemoved(position)
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    interface ReturnedListener {
        fun returned(toolsRentDto: ToolsRentDto)
    }
}