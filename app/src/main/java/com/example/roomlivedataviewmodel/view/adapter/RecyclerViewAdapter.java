package com.example.roomlivedataviewmodel.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DiffUtil.Callback;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomlivedataviewmodel.R;
import com.example.roomlivedataviewmodel.databinding.BorrowItemBinding;
import com.example.roomlivedataviewmodel.db.entity.BorrowModel;
import com.example.roomlivedataviewmodel.view.callback.DeleteItemCallback;

import java.util.List;
import java.util.Objects;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    List<? extends BorrowModel> mBorrowModelList;
    @Nullable
    private final DeleteItemCallback deleteItemCallback;



    public RecyclerViewAdapter(@Nullable DeleteItemCallback deleteItemCallback) {
        this.deleteItemCallback = deleteItemCallback;
        setHasStableIds(false);
    }

    public void setItemList(final List<? extends  BorrowModel> borrowModelList) {
         if(this.mBorrowModelList == null) {
             this.mBorrowModelList = borrowModelList;
             notifyItemRangeInserted(0,borrowModelList.size());
         } else {
             DiffUtil.DiffResult result = DiffUtil.calculateDiff(new Callback() {
                 @Override
                 public int getOldListSize() {
                     return mBorrowModelList.size();
                 }

                 @Override
                 public int getNewListSize() {
                     return borrowModelList.size();
                 }

                 @Override
                 public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                     return mBorrowModelList.get(oldItemPosition).getId() ==
                             borrowModelList.get(newItemPosition).getId();
                 }

                 @Override
                 public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                     BorrowModel newProduct = borrowModelList.get(newItemPosition);
                     BorrowModel oldProduct = mBorrowModelList.get(oldItemPosition);
                     return newProduct.getItemName().equals(oldProduct.getItemName())
                             && (newProduct.getId() == oldProduct.getId())
                             && Objects.equals(newProduct.getPersonName(), oldProduct.getPersonName());
                 }
             });
             mBorrowModelList = borrowModelList;
             result.dispatchUpdatesTo(this);
         }
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BorrowItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),R.layout.borrow_item,
                        parent,false);
        binding.setCallback(deleteItemCallback);
        return  new RecyclerViewHolder(binding);

    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.binding.setBorrowModel(mBorrowModelList.get(position));
        holder.binding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return mBorrowModelList == null ? 0 : mBorrowModelList.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        final BorrowItemBinding binding;

        public RecyclerViewHolder(BorrowItemBinding binding) {

            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
