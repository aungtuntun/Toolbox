package com.imceits.aungtuntun.alephcodeassignment.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imceits.aungtuntun.alephcodeassignment.R;
import com.imceits.aungtuntun.alephcodeassignment.data.pojos.ToolsRentDto;
import com.imceits.aungtuntun.alephcodeassignment.databinding.RentItemBinding;

import java.util.ArrayList;
import java.util.List;

public class DetailAdapter extends BaseAdapter<DetailAdapter.DetailViewHolder, ToolsRentDto> {
    private List<ToolsRentDto> dataList = new ArrayList<>();
    private final ReturnedListener listener;
    private Context mContext;
    DetailAdapter(Context context, ReturnedListener listener) {
        this.listener = listener;
        this.mContext = context;
    }

    @Override
    public void setData(List<ToolsRentDto> toolsRents) {
        dataList = new ArrayList<>();
        dataList = toolsRents;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RentItemBinding itemBinding = RentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DetailViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        holder.onBind(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class DetailViewHolder extends RecyclerView.ViewHolder {
        RentItemBinding itemBinding;

        DetailViewHolder(RentItemBinding rentItemBinding) {
            super(rentItemBinding.getRoot());
            itemBinding = rentItemBinding;
        }

        void onBind(ToolsRentDto toolsRent) {
            itemBinding.setRent(toolsRent);
            itemBinding.executePendingBindings();
            itemBinding.cardRentTool.setOnClickListener(v -> createDialog(toolsRent, getAdapterPosition()));
        }
    }

    private void createDialog(ToolsRentDto toolsRent, int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        dialogBuilder.setTitle(mContext.getString(R.string.title_return));
        dialogBuilder.setMessage("Are you sure to mark this tool as returned?");
        dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        dialogBuilder.setPositiveButton("OK", (dialog, which) -> {
            listener.returned(toolsRent);
            dataList.remove(toolsRent);
            notifyItemRemoved(position);
        });
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    public interface ReturnedListener {
        void returned(ToolsRentDto toolsRent);
    }
}
