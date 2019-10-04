package com.example.roomlivedataviewmodel.viewmodel;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.roomlivedataviewmodel.db.AppDatabase;
import com.example.roomlivedataviewmodel.db.entity.BorrowModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddBorrowItemViewModel extends AndroidViewModel {
    public MutableLiveData<String> itemName = new MutableLiveData<>();
    public MutableLiveData<String> personName = new MutableLiveData<>();

    public AppDatabase appDatabase;

    private Disposable mDisposable;

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
        appDatabase.itemAndPersonelModel().addBorrow(borrowModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .delay(7, TimeUnit.SECONDS)
                .subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

                Toast.makeText(getApplication().getApplicationContext(),"start delay-> delay 7 seconds", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onComplete() {

                Toast.makeText(getApplication().getApplicationContext(),"item added -> delay 7 seconds", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(Throwable e) {

                Toast.makeText(getApplication().getApplicationContext(),"Error -> " +e, Toast.LENGTH_LONG).show();

            }
        });

    }



//    private static class addAsyncTask extends  AsyncTask<BorrowModel, Void, Void> {
//        private  AppDatabase db;
//        addAsyncTask(AppDatabase appDatabase){
//            db = appDatabase;
//        }
//
//        @Override
//        protected Void doInBackground(BorrowModel... params) {
//            db.itemAndPersonelModel().addBorrow(params [0]);
//            return null;
//        }
//    }


}
