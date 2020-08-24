package com.azstudio.storeapp.Repository;

import androidx.lifecycle.MutableLiveData;

import com.azstudio.storeapp.Models.responseModel;
import com.azstudio.storeapp.Retro.GetDataService;
import com.azstudio.storeapp.Retro.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepo {


    private static LoginRepo loginRepo;

    public static LoginRepo getInstance(){
        if (loginRepo == null){
            loginRepo = new LoginRepo();
        }
        return loginRepo;
    }

    private GetDataService getDataService;

    public LoginRepo(){
        getDataService = RetrofitClientInstance.cteateService(GetDataService.class);
    }

    public MutableLiveData<responseModel> login(String email,String password){

        final MutableLiveData<responseModel> loginrespose = new MutableLiveData<>();
        getDataService.login(email,password).enqueue(new Callback<responseModel>() {

            @Override
            public void onResponse(Call<responseModel> call,
                                   Response<responseModel> response) {

                if (response.isSuccessful()){

                    loginrespose.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<responseModel> call, Throwable t) {
                System.out.println("fail"+t.getMessage());
                loginrespose.setValue(null);
            }
        });
        return loginrespose;
    }
}
