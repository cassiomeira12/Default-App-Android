package com.android.app.view.dashboard;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.app.contract.IUser;
import com.android.app.data.model.BaseUser;
import com.android.app.presenter.login.UserPresenter;

import org.jetbrains.annotations.Nullable;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");

//        new UserPresenter(new IUser.View() {
//            @Override
//            public void onResult(@Nullable BaseUser user) {
//                Log.d("cassio", "deslogou");
//            }
//        }).signOut();


    }

    public LiveData<String> getText() {
        return mText;
    }
}