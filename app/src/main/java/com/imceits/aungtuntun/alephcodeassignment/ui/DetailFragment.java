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
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.imceits.aungtuntun.alephcodeassignment.data.Status;
import com.imceits.aungtuntun.alephcodeassignment.data.entity.ToolsRent;
import com.imceits.aungtuntun.alephcodeassignment.data.pojos.ToolsRentDto;
import com.imceits.aungtuntun.alephcodeassignment.databinding.DetailFragmentBinding;
import com.imceits.aungtuntun.alephcodeassignment.di.Injectable;
import com.imceits.aungtuntun.alephcodeassignment.utils.AutoClearedValue;

import javax.inject.Inject;

public class DetailFragment extends Fragment implements Injectable, DetailAdapter.ReturnedListener {

    private DetailViewModel mViewModel;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private AutoClearedValue<DetailFragmentBinding> binding;
    private int friendId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        DetailFragmentBinding dataBinding = DetailFragmentBinding.inflate(inflater, container, false);
        assert getArguments() != null;
        friendId = DetailFragmentArgs.fromBundle(getArguments()).getFriendId();
        binding = new AutoClearedValue<>(this, dataBinding);
        binding.get().setLifecycleOwner(this);
        binding.get().rentListView.setHasFixedSize(true);
        binding.get().rentListView.setNestedScrollingEnabled(false);
        binding.get().rentListView.setItemAnimator(new DefaultItemAnimator());
        DetailAdapter adapter = new DetailAdapter(getActivity(), this);
        binding.get().rentListView.setAdapter(adapter);
        return binding.get().getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new  ViewModelProvider(this, viewModelFactory).get(DetailViewModel.class);
        binding.get().setViewModel(mViewModel);
        mViewModel.updateId(friendId);

    }

    @Override
    public void returned(ToolsRentDto toolsRentDto) {
        ToolsRent toolsRent = new ToolsRent();
        toolsRent.setStatus(Status.RETURNED);
        toolsRent.setToolId(toolsRentDto.getTool_id());
        toolsRent.setId(toolsRentDto.getId());
        mViewModel.updateToolsRent(toolsRent);
        showMessage();
    }

    private void showMessage() {
        Toast.makeText(getActivity(), "Returned successfully!", Toast.LENGTH_LONG).show();
    }
}
