package com.azstudio.storeapp.Repository;

import androidx.lifecycle.MutableLiveData;

import com.azstudio.storeapp.Models.responseModel;
import com.azstudio.storeapp.Models.user;
import com.azstudio.storeapp.Retro.GetDataService;
import com.azstudio.storeapp.Retro.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdduserRepo {

    private static AdduserRepo adduserRepo;

    public static AdduserRepo getInstance(){
        if (adduserRepo == null){
            adduserRepo = new AdduserRepo();
        }
        return adduserRepo;
    }

    private GetDataService getDataService;

    public AdduserRepo(){
        getDataService = RetrofitClientInstance.cteateService(GetDataService.class);
    }

    public MutableLiveData<responseModel> adduser(user user){

        final MutableLiveData<responseModel> adduseresponse = new MutableLiveData<>();
        getDataService.adduser(user.getUser_name(),user.getEmail(),user.getUser_password(),user.getUser_role()).enqueue(new Callback<responseModel>() {

            @Override
            public void onResponse(Call<responseModel> call,
                                   Response<responseModel> response) {
                System.out.println("respose"+response);
                if (response.isSuccessful()){
                    System.out.println("success"+response.message());
                    adduseresponse.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<responseModel> call, Throwable t) {
                System.out.println("fail"+t.getMessage());
                adduseresponse.setValue(null);
            }
        });
        return adduseresponse;
    }
}
