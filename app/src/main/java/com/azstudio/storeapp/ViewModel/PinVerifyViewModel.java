package com.azstudio.storeapp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.azstudio.storeapp.Models.pinVerify;
import com.azstudio.storeapp.Models.responseModel;
import com.azstudio.storeapp.Repository.PinRepo;

public class PinVerifyViewModel extends ViewModel {
    private MutableLiveData<responseModel> mutableLiveData;
    private PinRepo pinRepo;


    public PinVerifyViewModel() {

        pinRepo=  pinRepo.getInstance();

    }

    public LiveData<responseModel> pinverify(pinVerify pinVerify) {

            mutableLiveData = pinRepo.pinverify(pinVerify);

        return mutableLiveData;
    }



}