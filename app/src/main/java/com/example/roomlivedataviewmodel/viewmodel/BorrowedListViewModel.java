package com.example.roomlivedataviewmodel.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.roomlivedataviewmodel.db.AppDatabase;
import com.example.roomlivedataviewmodel.db.entity.BorrowModel;
import com.example.roomlivedataviewmodel.view.ui.AddItem;

import java.util.List;

public class BorrowedListViewModel extends AndroidViewModel {



    private final MediatorLiveData<List<BorrowModel>> listLiveData;

    private AppDatabase appDatabase;

    public BorrowedListViewModel(@NonNull Application application) {
        super(application);

        this.listLiveData = new MediatorLiveData<>();
        this.listLiveData.setValue(null);

        appDatabase = AppDatabase.getDatabase(this.getApplication());

        this.listLiveData.addSource(appDatabase.itemAndPersonelModel().getAllBorrowedItems(), this.listLiveData::setValue);
    }

    public  LiveData<List<BorrowModel>> getListLiveData() {
        return listLiveData;
    }

    public void deleteItemLiveData(BorrowModel borrowModel) {
        new deleteAsyncTask(appDatabase).execute(borrowModel);
    }

    private static class deleteAsyncTask extends AsyncTask<BorrowModel,Void, Void> {

        private AppDatabase appDatabase;
        public deleteAsyncTask(AppDatabase appDatabase) {
            this.appDatabase = appDatabase;
        }

        @Override
        protected Void doInBackground(final BorrowModel... params) {
            this.appDatabase.itemAndPersonelModel().deleteBorrow(params[0]);
            return null;
        }
    }
}
