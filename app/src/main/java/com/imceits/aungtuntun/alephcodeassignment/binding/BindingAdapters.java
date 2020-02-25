package com.imceits.aungtuntun.alephcodeassignment.binding;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.imceits.aungtuntun.alephcodeassignment.data.entity.Friends;
import com.imceits.aungtuntun.alephcodeassignment.data.pojos.ToolsDto;
import com.imceits.aungtuntun.alephcodeassignment.data.pojos.ToolsRentDto;
import com.imceits.aungtuntun.alephcodeassignment.ui.BaseAdapter;

import java.util.List;

public class BindingAdapters {

    @SuppressWarnings("unchecked")
    @BindingAdapter("tool_list")
    public static void setTools(RecyclerView recyclerView, List<ToolsDto> data) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null) {
            return;
        }
        if (data == null) {
            return;
        }
        if (adapter instanceof BaseAdapter) {
            ((BaseAdapter) adapter).setData(data);
        }
    }
    @SuppressWarnings("unchecked")
    @BindingAdapter("friend_list")
    public static void setFriends(RecyclerView recyclerView, List<Friends> data) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null) {
            return;
        }
        if (data == null) {
            return;
        }
        if (adapter instanceof BaseAdapter) {
            ((BaseAdapter) adapter).setData(data);
        }
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter("rent_list")
    public static void setToolRentList(RecyclerView recyclerView, List<ToolsRentDto> data) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null) {
            return;
        }
        if (data == null) {
            return;
        }
        if (adapter instanceof BaseAdapter) {
            ((BaseAdapter) adapter).setData(data);
        }
    }
    @BindingAdapter("image_id")
    public static void setIcon(ImageView imageView, String resName) {
        if (!TextUtils.isEmpty(resName)) {
            int resId = Integer.parseInt(resName);
            imageView.setImageResource(resId);
        }
    }
}
