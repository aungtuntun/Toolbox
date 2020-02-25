package com.imceits.aungtuntun.toolsbox.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import com.imceits.aungtuntun.toolsbox.data.Status
import com.imceits.aungtuntun.toolsbox.data.entity.Friends
import com.imceits.aungtuntun.toolsbox.data.entity.ToolsRent
import com.imceits.aungtuntun.toolsbox.data.views.ToolsDto

import com.imceits.aungtuntun.toolsbox.databinding.FragmentToolsBinding
import java.util.*

class ToolsFragment : Fragment(), ToolsAdapter.BorrowListener {

    private lateinit var viewModel: ToolsViewModel
    private lateinit var dataBinding: FragmentToolsBinding
    private lateinit var adapter: ToolsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        dataBinding = FragmentToolsBinding.inflate(inflater, container, false)
        dataBinding.lifecycleOwner = this
        dataBinding.toolList.setHasFixedSize(true)
        dataBinding.toolList.isNestedScrollingEnabled = false
        dataBinding.toolList.itemAnimator = DefaultItemAnimator()
        adapter = ToolsAdapter(requireContext(), this)
        dataBinding.toolList.adapter = adapter
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ToolsViewModel::class.java)
        dataBinding.viewModel = viewModel
        viewModel.toolsList.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })
        viewModel.friendList.observe(viewLifecycleOwner, Observer {
            adapter.setFriendList(it)
        })

    }

    override fun borrowed(tool: ToolsDto, friend: Friends?) {
        friend?.let {
           val toolsRent = ToolsRent(0, toolId = tool.id, friendId = friend.id,
               status = Status.BORROWED, createdDate = Date(), returnedCount = 0, count = 1
           )
            viewModel.insertToolsRent(toolsRent)
            showMessage(tool.name + " borrow to " + friend.name + " successfully!" )
        }?: showMessage("Enter correct name!")
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}
