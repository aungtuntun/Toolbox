package com.imceits.aungtuntun.toolsbox.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import com.imceits.aungtuntun.toolsbox.data.Status
import com.imceits.aungtuntun.toolsbox.data.entity.ToolsRent
import com.imceits.aungtuntun.toolsbox.data.views.ToolsRentDto
import com.imceits.aungtuntun.toolsbox.databinding.FragmentRentBinding
import java.util.*

class RentFragment : Fragment(), DetailAdapter.ReturnedListener {


    private lateinit var viewModel: RentViewModel
    private lateinit var rentBinding: FragmentRentBinding
    private lateinit var adapter: DetailAdapter
    private var friendId: Int = 0

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        rentBinding = FragmentRentBinding.inflate(inflater, container, false)
        friendId = RentFragmentArgs.fromBundle(arguments!!).friendId
        rentBinding.rentList.setHasFixedSize(true)
        rentBinding.rentList.isNestedScrollingEnabled = false
        rentBinding.rentList.itemAnimator = DefaultItemAnimator()
        adapter = DetailAdapter(requireContext(), this)
        rentBinding.rentList.adapter = adapter

        return rentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RentViewModel::class.java)
        rentBinding.viewModel = viewModel
        viewModel.updateId(friendId)

        viewModel.friendLiveData.observe(viewLifecycleOwner, Observer {
            rentBinding.friend = it
        })
        viewModel.toolsRentLiveData.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })

    }


    private fun String.showMessage() {
        Toast.makeText(requireContext(), this, Toast.LENGTH_SHORT).show()
    }

    override fun returned(toolsRentDto: ToolsRentDto) {
        val toolsRent = ToolsRent(id = toolsRentDto.id, toolId = toolsRentDto.toolId, friendId = toolsRentDto.friendId,
            status = Status.RETURNED, count = 0, returnedCount = toolsRentDto.count, createdDate = Date())
        viewModel.updateToolsRent(toolsRent)
        "Returned successfully!".showMessage()
    }
}
