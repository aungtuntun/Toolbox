package com.imceits.aungtuntun.alephcodeassignment.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.imceits.aungtuntun.alephcodeassignment.data.entity.Friends;
import com.imceits.aungtuntun.alephcodeassignment.databinding.FriendFragmentBinding;
import com.imceits.aungtuntun.alephcodeassignment.di.Injectable;
import com.imceits.aungtuntun.alephcodeassignment.utils.AutoClearedValue;

import java.util.List;

import javax.inject.Inject;

public class FriendFragment extends Fragment implements Injectable {

    private FriendViewModel mViewModel;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private AutoClearedValue<FriendFragmentBinding> binding;

    public static FriendFragment newInstance() {
        return new FriendFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FriendFragmentBinding dataBinding = FriendFragmentBinding.inflate(inflater, container, false);
        binding = new AutoClearedValue<>(this, dataBinding);
        binding.get().setLifecycleOwner(this);
        binding.get().friendList.setHasFixedSize(true);
        binding.get().friendList.setNestedScrollingEnabled(false);
        binding.get().friendList.setItemAnimator(new DefaultItemAnimator());
        binding.get().friendList.setAdapter(new FriendAdapter());
        return binding.get().getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new  ViewModelProvider(this, viewModelFactory).get(FriendViewModel.class);
        binding.get().setViewModel(mViewModel);
        mViewModel.getFriendsList().observe(getViewLifecycleOwner(), new Observer<List<Friends>>() {
            @Override
            public void onChanged(List<Friends> friends) {

            }
        });
    }

}
