package com.imceits.aungtuntun.alephcodeassignment.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.imceits.aungtuntun.alephcodeassignment.data.entity.Friends;
import com.imceits.aungtuntun.alephcodeassignment.databinding.FriendItemBinding;

import java.util.ArrayList;
import java.util.List;

public class FriendAdapter extends BaseAdapter<FriendAdapter.FriendViewHolder, Friends> {

    private List<Friends> friendsList = new ArrayList<>();

    @Override
    public void setData(List<Friends> friends) {
        friendsList = new ArrayList<>();
        friendsList = friends;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FriendItemBinding itemBinding = FriendItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new FriendViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        holder.onBind(friendsList.get(position));
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }

    class FriendViewHolder extends RecyclerView.ViewHolder {
        FriendItemBinding itemBinding;

        FriendViewHolder(FriendItemBinding friendItemBinding) {
            super(friendItemBinding.getRoot());
            itemBinding = friendItemBinding;
        }

        void onBind(Friends friends) {
            itemBinding.setFriend(friends);
            itemBinding.executePendingBindings();
            FriendFragmentDirections.ActionFriendFragmentToDetailFragment direction =
                    FriendFragmentDirections.actionFriendFragmentToDetailFragment();
            direction.setFriendId(friends.getId());
            itemBinding.cardFriend.setOnClickListener(Navigation.createNavigateOnClickListener(direction));
        }
    }
}
