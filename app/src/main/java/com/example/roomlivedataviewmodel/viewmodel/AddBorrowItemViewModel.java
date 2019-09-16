package com.example.roomlivedataviewmodel.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.roomlivedataviewmodel.db.AppDatabase;
import com.example.roomlivedataviewmodel.db.entity.BorrowModel;

public class AddBorrowItemViewModel extends AndroidViewModel {
    public MutableLiveData<String> itemName = new MutableLiveData<>();
    public MutableLiveData<String> personName = new MutableLiveData<>();

    public AppDatabase appDatabase;

    private MutableLiveData<BorrowModel> borrowModelMutableLiveData;

    public AddBorrowItemViewModel(@NonNull Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());

    }

    public MutableLiveData<BorrowModel> getBorrowModel(){
        if (borrowModelMutableLiveData == null) {
            borrowModelMutableLiveData = new MutableLiveData<>();
        }
        return borrowModelMutableLiveData;
    }


    public void addNewItemInDB(){
        BorrowModel borrowModel = new BorrowModel(itemName.getValue(),personName.getValue());
        new addAsyncTask(appDatabase).execute(borrowModel);
    }

    private static class addAsyncTask extends  AsyncTask<BorrowModel, Void, Void> {
        private  AppDatabase db;
        addAsyncTask(AppDatabase appDatabase){
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(BorrowModel... params) {
            db.itemAndPersonelModel().addBorrow(params [0]);
            return null;
        }
    }


}
