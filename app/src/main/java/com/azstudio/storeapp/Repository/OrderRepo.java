package com.azstudio.storeapp.Repository;

import androidx.lifecycle.MutableLiveData;

import com.azstudio.storeapp.Models.orderDetails;
import com.azstudio.storeapp.Retro.GetDataService;
import com.azstudio.storeapp.Retro.RetrofitClientInstance;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRepo {
    private static OrderRepo orderRepo;

    public static OrderRepo getInstance(){
        if (orderRepo == null){
            orderRepo = new OrderRepo();
        }
        return orderRepo;
    }

    private GetDataService getDataService;

    public OrderRepo(){
        getDataService = RetrofitClientInstance.cteateService(GetDataService.class);
    }

    public MutableLiveData<List<orderDetails>> getorder(String email){
        System.out.println("mail"+email);
        final MutableLiveData<List<orderDetails>> oderdetails = new MutableLiveData<>();
        getDataService.getorder(email).enqueue(new Callback<List<orderDetails>>() {

            @Override
            public void onResponse(Call<List<orderDetails>> call,
                                   Response<List<orderDetails>> response) {
                System.out.println("respose"+response);
                if (response.isSuccessful()){
                    System.out.println("success"+response.message());
                    oderdetails.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<orderDetails>> call, Throwable t) {
                System.out.println("faildsgs"+t.getMessage());
                oderdetails.setValue(null);
            }
        });
        return oderdetails;
    }
    public MutableLiveData<List<orderDetails>> addorder(List<orderDetails> obj){

        final MutableLiveData<List<orderDetails>> oderdetails = new MutableLiveData<>();
        getDataService.addorder(obj).enqueue(new Callback<List<orderDetails>>() {

            @Override
            public void onResponse(Call<List<orderDetails>> call,
                                   Response<List<orderDetails>> response) {
                System.out.println("respose"+response);
                if (response.isSuccessful()){
                    System.out.println("success"+response.message());
                    oderdetails.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<orderDetails>> call, Throwable t) {
                System.out.println("faildsgs"+t.getMessage());
                oderdetails.setValue(null);
            }
        });
        return oderdetails;
    }

    public void sentsms(String number,String product){
        getDataService.sentsms(number,product).enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call,
                                   Response<String> response) {
                System.out.println("sms "+response);

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("faildsgs"+t.getMessage());

            }
        });

    }
}
