package com.example.roomlivedataviewmodel.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

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

import org.reactivestreams.Subscription;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.CompletableObserver;
import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private RecyclerViewAdapter recyclerViewAdapter;

    private ActivityMainBinding activityMainBinding;
    private BorrowedListViewModel borrowedListViewModel;

    private Disposable mDisposable = new CompositeDisposable();



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

    @Override
    protected void onStop() {
        super.onStop();
        mDisposable.dispose();
    }

    private void observe() {
        borrowedListViewModel.getListFlowable()
                .subscribeOn(Schedulers.io())
                .delay(3,TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<List<BorrowModel>>() {
                               @Override
                               public void onSubscribe(Subscription s) {
                                   activityMainBinding.setLoading(true);
                                   s.request(Long.MAX_VALUE);
                               }

                               @Override
                               public void onNext(List<BorrowModel> borrowModelList) {
                                   recyclerViewAdapter.setItemList(borrowModelList);
                                   activityMainBinding.executePendingBindings();
                                   activityMainBinding.setLoading(false);

                               }

                               @Override
                               public void onError(Throwable t) {
                                   Toast.makeText(getApplicationContext()
                                           ,"data error -> " + t
                                           ,Toast.LENGTH_LONG)
                                           .show();
                               }

                               @Override
                               public void onComplete() {
                               }
                           });
//                        borrowModelList -> {
//                            if (borrowModelList != null) {
//                                recyclerViewAdapter.setItemList(borrowModelList);
//                                activityMainBinding.executePendingBindings();
//                                activityMainBinding.setLoading(false);
//                            } else {
//                                activityMainBinding.setLoading(true);
//                            }
//                        },
//                        error -> Toast.makeText(this,
//                                "data error -> " + error,
//                                Toast.LENGTH_LONG)
//                                .show());
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
