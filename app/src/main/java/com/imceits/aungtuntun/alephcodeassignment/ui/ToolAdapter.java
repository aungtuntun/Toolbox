package com.imceits.aungtuntun.alephcodeassignment.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imceits.aungtuntun.alephcodeassignment.R;
import com.imceits.aungtuntun.alephcodeassignment.data.entity.Friends;
import com.imceits.aungtuntun.alephcodeassignment.data.pojos.ToolsDto;
import com.imceits.aungtuntun.alephcodeassignment.databinding.ToolItemBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ToolAdapter extends BaseAdapter<ToolAdapter.ToolViewHolder, ToolsDto> {
private List<ToolsDto> toolsList = new ArrayList<>();
private final BorrowListener listener;
private List<String> friendNameList = new ArrayList<>();
private List<Friends> friendsList = new ArrayList<>();
private Context mContext;

    ToolAdapter(Context context, BorrowListener listener) {
        this.listener = listener;
        this.mContext = context;
    }

    @Override
    public void setData(List<ToolsDto> tools) {
        toolsList = new ArrayList<>();
        toolsList = tools;
        notifyDataSetChanged();
    }

    void setFriendList(List<Friends> friendList) {
        this.friendsList = friendList;
        if (!friendList.isEmpty()) {
            for (Friends friend : friendList) {
                friendNameList.add(friend.getName());
            }
        }
    }

    @NonNull
    @Override
    public ToolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ToolItemBinding itemBinding = ToolItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new ToolViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ToolViewHolder holder, int position) {
        holder.onBind(toolsList.get(position));
    }

    @Override
    public int getItemCount() {
        return toolsList.size();
    }

    class ToolViewHolder extends RecyclerView.ViewHolder {
        ToolItemBinding itemBinding;
        ToolViewHolder(ToolItemBinding toolItemBinding) {
            super(toolItemBinding.getRoot());
            this.itemBinding = toolItemBinding;
        }

        void onBind(ToolsDto tools) {
            itemBinding.setData(tools);
            itemBinding.executePendingBindings();
            itemBinding.cardTool.setOnClickListener(v -> createDialog(tools));
        }
    }

    private void createDialog(ToolsDto tools) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        dialogBuilder.setTitle(mContext.getString(R.string.txt_auto_name));
        LayoutInflater inflater = LayoutInflater.from(mContext);
        @SuppressLint("InflateParams") final View dialogView = inflater.inflate(R.layout.search_friend, null);
        AutoCompleteTextView autoView = dialogView.findViewById(R.id.auto_name);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.select_dialog_item, friendNameList);
        autoView.setThreshold(1);
        autoView.setAdapter(adapter);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setPositiveButton("OK", (dialog, which) -> {
            String name = autoView.getText().toString();
            Friends friends = getFriend(name);
            listener.borrowed(tools, friends);
        });
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    private Friends getFriend(String name) {
        for (Friends fri :  friendsList) {
            if (Objects.equals(name, fri.getName())) {
                return fri;
            }
        }
        return null;
    }

    public interface BorrowListener {
        void borrowed(ToolsDto tools, Friends friends);
    }
}
