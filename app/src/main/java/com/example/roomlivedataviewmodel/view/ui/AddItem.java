package com.example.roomlivedataviewmodel.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import com.example.roomlivedataviewmodel.R;
import com.example.roomlivedataviewmodel.databinding.ActivityAddItemBinding;
import com.example.roomlivedataviewmodel.db.entity.BorrowModel;
import com.example.roomlivedataviewmodel.view.callback.BackToViewCallback;
import com.example.roomlivedataviewmodel.viewmodel.AddBorrowItemViewModel;

public class AddItem extends AppCompatActivity {

    private ActivityAddItemBinding activityAddItemBinding;
    private AddBorrowItemViewModel addBorrowItemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addBorrowItemViewModel = ViewModelProviders.of(this).get(AddBorrowItemViewModel.class);

        activityAddItemBinding = DataBindingUtil.setContentView(this,R.layout.activity_add_item);


        activityAddItemBinding.setAddBorrowItemViewModel(addBorrowItemViewModel);
        activityAddItemBinding.setCallback(backToViewCallback);

        addBorrowItemViewModel.getBorrowModel().observe(this, borrowModel -> {
            activityAddItemBinding.itemNameEditText.setText(borrowModel.getItemName());
            activityAddItemBinding.personNameEditText.setText(borrowModel.getPersonName());
        });


    }

    private final BackToViewCallback backToViewCallback = new BackToViewCallback() {
        @Override
        public void onClick() {
            addBorrowItemViewModel.addNewItemInDB();
            onBackPressed();
        }
    };
}
