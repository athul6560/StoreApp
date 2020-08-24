package com.azstudio.storeapp.Repository;

import androidx.lifecycle.MutableLiveData;

import com.azstudio.storeapp.Models.pinVerify;
import com.azstudio.storeapp.Models.responseModel;
import com.azstudio.storeapp.Retro.GetDataService;
import com.azstudio.storeapp.Retro.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PinRepo {
    private static PinRepo pinRepo;

    public static PinRepo getInstance(){
        if (pinRepo == null){
            pinRepo = new PinRepo();
        }
        return pinRepo;
    }

    private GetDataService getDataService;

    public PinRepo(){
        getDataService = RetrofitClientInstance.cteateService(GetDataService.class);
    }

    public MutableLiveData<responseModel> pinverify(pinVerify pinVerify){

        final MutableLiveData<responseModel> verifyresponse = new MutableLiveData<>();
        getDataService.pinverify(pinVerify.getUser_email(),pinVerify.getPin()).enqueue(new Callback<responseModel>() {

            @Override
            public void onResponse(Call<responseModel> call,
                                   Response<responseModel> response) {
                System.out.println("respose"+response);
                if (response.isSuccessful()){
                    System.out.println("success"+response);
                    verifyresponse.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<responseModel> call, Throwable t) {
                System.out.println("fail"+t.getMessage());
                verifyresponse.setValue(null);
            }
        });
        return verifyresponse;
    }
}
