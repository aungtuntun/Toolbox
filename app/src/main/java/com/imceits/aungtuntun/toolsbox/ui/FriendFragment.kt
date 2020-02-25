package com.imceits.aungtuntun.toolsbox.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import com.imceits.aungtuntun.toolsbox.databinding.FragmentFriendBinding

class FriendFragment : Fragment() {

    private lateinit var viewModel: FriendViewModel
    private lateinit var dataBinding: FragmentFriendBinding
    private lateinit var adapter: FriendAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = FragmentFriendBinding.inflate(inflater, container, false)
        dataBinding.lifecycleOwner = this
        dataBinding.friendList.setHasFixedSize(true)
        dataBinding.friendList.isNestedScrollingEnabled = false
        dataBinding.friendList.itemAnimator = DefaultItemAnimator()
        adapter = FriendAdapter()
        dataBinding.friendList.adapter = adapter
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FriendViewModel::class.java)
        dataBinding.viewModel = viewModel
        viewModel.friendsList.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })

    }

}
