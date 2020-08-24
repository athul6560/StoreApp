package com.azstudio.storeapp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.azstudio.storeapp.Models.responseModel;
import com.azstudio.storeapp.Repository.registerRepo;

public class regisetrViewModel extends ViewModel {
    private MutableLiveData<responseModel> mailresponse;
    private registerRepo registerRepo;

    public regisetrViewModel(){

        registerRepo=   registerRepo.getInstance();
    }

    public LiveData<responseModel> getmailresponse(String email) {


            mailresponse = registerRepo.sentmail(email);


        return mailresponse;
    }


}