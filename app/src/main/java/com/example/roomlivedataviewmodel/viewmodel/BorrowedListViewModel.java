package com.example.roomlivedataviewmodel.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.roomlivedataviewmodel.db.AppDatabase;
import com.example.roomlivedataviewmodel.db.entity.BorrowModel;

import org.reactivestreams.Subscriber;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BorrowedListViewModel extends AndroidViewModel {




    private AppDatabase appDatabase;


    public BorrowedListViewModel(@NonNull Application application) {
        super(application);


        appDatabase = AppDatabase.getDatabase(this.getApplication());

    }

    public  Flowable<List<BorrowModel>> getListFlowable() {

        return appDatabase.itemAndPersonelModel().getAllBorrowedItems()
                .filter(listFlowable -> !listFlowable.isEmpty());

    }


    public void deleteItemLiveData(final BorrowModel borrowModel) {
        Completable.fromAction(() -> appDatabase.itemAndPersonelModel().deleteBorrow(borrowModel))
                .delay(3000,TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).
                subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                Toast.makeText(getApplication().getApplicationContext(),"starting delay ->  3 seconds", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onComplete() {
                Toast.makeText(getApplication().getApplicationContext(),"item deleted -> delay 3 seconds", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getApplication().getApplicationContext(),"Error -> " + e, Toast.LENGTH_LONG).show();
            }
        });

    }

//    private static class deleteAsyncTask extends AsyncTask<BorrowModel,Void, Void> {
//
//        private AppDatabase appDatabase;
//        public deleteAsyncTask(AppDatabase appDatabase) {
//            this.appDatabase = appDatabase;
//        }
//
//        @Override
//        protected Void doInBackground(final BorrowModel... params) {
//            this.appDatabase.itemAndPersonelModel().deleteBorrow(params[0]);
//            return null;
//        }
//    }
}
