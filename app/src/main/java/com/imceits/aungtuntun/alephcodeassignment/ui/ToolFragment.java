package com.imceits.aungtuntun.alephcodeassignment.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.imceits.aungtuntun.alephcodeassignment.data.Status;
import com.imceits.aungtuntun.alephcodeassignment.data.entity.Friends;
import com.imceits.aungtuntun.alephcodeassignment.data.entity.Tools;
import com.imceits.aungtuntun.alephcodeassignment.data.entity.ToolsRent;
import com.imceits.aungtuntun.alephcodeassignment.data.pojos.ToolsDto;
import com.imceits.aungtuntun.alephcodeassignment.databinding.ToolFragmentBinding;
import com.imceits.aungtuntun.alephcodeassignment.di.Injectable;
import com.imceits.aungtuntun.alephcodeassignment.utils.AutoClearedValue;

import java.util.Date;

import javax.inject.Inject;

public class ToolFragment extends Fragment implements Injectable, ToolAdapter.BorrowListener {

    private ToolViewModel mViewModel;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private AutoClearedValue<ToolFragmentBinding> binding;

    private ToolAdapter toolAdapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ToolFragmentBinding dataBinding = ToolFragmentBinding.inflate(inflater, container, false);
        binding = new AutoClearedValue<>(this, dataBinding);
        binding.get().setLifecycleOwner(this);
        binding.get().toolList.setHasFixedSize(true);
        binding.get().toolList.setNestedScrollingEnabled(false);
        binding.get().toolList.setItemAnimator(new DefaultItemAnimator());
        toolAdapter = new ToolAdapter(getActivity(),this);
        binding.get().toolList.setAdapter(toolAdapter);
        return binding.get().getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(ToolViewModel.class);
        binding.get().setViewModel(mViewModel);
        mViewModel.getFriendsList().observe(getViewLifecycleOwner(), friends -> toolAdapter.setFriendList(friends));
    }

    @Override
    public void borrowed(ToolsDto tools, Friends friends) {
        if (friends != null) {
            ToolsRent toolsRent = new ToolsRent();
            toolsRent.setStatus(Status.BORROWED);
            toolsRent.setFriendId(friends.getId());
            toolsRent.setToolId(tools.getId());
            toolsRent.setCount(1);
            toolsRent.setCreatedDate(new Date());
            mViewModel.insertToolsRent(toolsRent);
            showMessage("Borrowed successfully!");
        }else {
            showMessage("Enter correct name");
        }
    }

    private void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
}
