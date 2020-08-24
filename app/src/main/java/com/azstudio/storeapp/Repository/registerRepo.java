package com.azstudio.storeapp.Repository;

import androidx.lifecycle.MutableLiveData;

import com.azstudio.storeapp.Models.responseModel;
import com.azstudio.storeapp.Retro.GetDataService;
import com.azstudio.storeapp.Retro.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class registerRepo {
    private static registerRepo registerRepo;

    public static registerRepo getInstance(){
        if (registerRepo == null){
            registerRepo = new registerRepo();
        }
        return registerRepo;
    }

    private GetDataService getDataService;

    public registerRepo(){
        getDataService = RetrofitClientInstance.cteateService(GetDataService.class);
    }

    public MutableLiveData<responseModel> sentmail(String email){

        final MutableLiveData<responseModel> mailresponse = new MutableLiveData<>();
        getDataService.sentmail(email).enqueue(new Callback<responseModel>() {

            @Override
            public void onResponse(Call<responseModel> call,
                                   Response<responseModel> response) {
                System.out.println("respose"+response);
                if (response.isSuccessful()){
                    System.out.println("success"+response);
                    mailresponse.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<responseModel> call, Throwable t) {
                System.out.println("fail"+t.getMessage());
                mailresponse.setValue(null);
            }
        });
        return mailresponse;
    }
}
