package com.example.roomlivedataviewmodel.view.ui;

import android.content.Intent;
import android.os.Bundle;

import com.example.roomlivedataviewmodel.databinding.ActivityMainBinding;
import com.example.roomlivedataviewmodel.db.entity.BorrowModel;
import com.example.roomlivedataviewmodel.view.adapter.RecyclerViewAdapter;
import com.example.roomlivedataviewmodel.view.callback.AddItemCallback;
import com.example.roomlivedataviewmodel.view.callback.DeleteItemCallback;
import com.example.roomlivedataviewmodel.viewmodel.BorrowedListViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.roomlivedataviewmodel.R;

public class MainActivity extends AppCompatActivity {

    private RecyclerViewAdapter recyclerViewAdapter;

    private ActivityMainBinding activityMainBinding;
    private BorrowedListViewModel borrowedListViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        borrowedListViewModel = ViewModelProviders.of(this).get(BorrowedListViewModel.class);

        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        activityMainBinding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewAdapter = new RecyclerViewAdapter(deleteItemCallback);
        activityMainBinding.recycleView.setAdapter(recyclerViewAdapter);
        activityMainBinding.getRoot();

        observe();


        activityMainBinding.setCallback(mAddItemCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        observe();
    }

    private void observe(){
        borrowedListViewModel.getListLiveData().observe(this, borrowModelList -> {
            if (borrowModelList != null) {
                recyclerViewAdapter.setItemList(borrowModelList);
                activityMainBinding.executePendingBindings();
            }
        });
    }
    private final AddItemCallback mAddItemCallback = new AddItemCallback() {
        @Override
        public void onClick() {
            Intent intent = new Intent(getApplication(), AddItem.class);
            startActivity(intent);
        }
    };

    private final DeleteItemCallback deleteItemCallback = new DeleteItemCallback() {
        @Override
        public void onClick(BorrowModel borrowModel) {
            borrowedListViewModel.deleteItemLiveData(borrowModel);
        }
    };

}
