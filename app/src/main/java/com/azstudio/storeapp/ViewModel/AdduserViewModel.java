package com.azstudio.storeapp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.azstudio.storeapp.Models.responseModel;
import com.azstudio.storeapp.Models.user;
import com.azstudio.storeapp.Repository.AdduserRepo;

public class AdduserViewModel extends ViewModel {
    private MutableLiveData<responseModel> mutableLiveData;

    private AdduserRepo adduserRepo;

    public AdduserViewModel() {

        adduserRepo= adduserRepo.getInstance();
    }
    public LiveData<responseModel> adduser(user user) {

            mutableLiveData = adduserRepo.adduser(user);

        return mutableLiveData;
    }
}
