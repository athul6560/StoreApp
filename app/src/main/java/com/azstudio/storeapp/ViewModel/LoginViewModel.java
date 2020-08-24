package com.azstudio.storeapp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.azstudio.storeapp.Models.responseModel;
import com.azstudio.storeapp.Repository.LoginRepo;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<responseModel> mutableLiveData;

    private LoginRepo loginRepo;

    public LoginViewModel() {

        loginRepo=  loginRepo.getInstance();
    }
    public LiveData<responseModel> login(String email,String password) {

            mutableLiveData = loginRepo.login(email,password);

        return mutableLiveData;
    }
}
