package com.azstudio.storeapp.Repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.azstudio.storeapp.Models.Product;
import com.azstudio.storeapp.Models.product_outer_model;
import com.azstudio.storeapp.Retro.GetDataService;
import com.azstudio.storeapp.Retro.RetrofitClientInstance;
import com.azstudio.storeapp.dbHandler.DatabaseHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepo {

    private static ProductRepo productRepo;
    Product product;
    public static ProductRepo getInstance(){
        if (productRepo == null){
            productRepo = new ProductRepo();
        }
        return productRepo;
    }

    private GetDataService getDataService;

    public ProductRepo(){
        getDataService = RetrofitClientInstance.cteateService(GetDataService.class);
    }

    public MutableLiveData<product_outer_model> getAllProducts(){
        final MutableLiveData<product_outer_model> allproducts = new MutableLiveData<>();
        getDataService.getAllProducts().enqueue(new Callback<product_outer_model>() {
            @Override
            public void onResponse(Call<product_outer_model> call,
                                   Response<product_outer_model> response) {
                if (response.isSuccessful()){

                    allproducts.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<product_outer_model> call, Throwable t) {
                System.out.println("fail"+t.getMessage());
                allproducts.setValue(null);
            }
        });
        return allproducts;
}
    public MutableLiveData<String> getcartcount(Context context){
        final MutableLiveData<String> count = new MutableLiveData<>();
        DatabaseHandler databaseHandler= new DatabaseHandler(context);
        count.postValue(String.valueOf(databaseHandler.getAllDetails().size()));
        return count;
    }

    public MutableLiveData<Product> getproduct(String title){
        final MutableLiveData<Product> allproducts = new MutableLiveData<>();

        getDataService.getproduct(title).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call,
                                   Response<Product> response) {

                if (response.isSuccessful()){

                    allproducts.postValue(response.body());
                }else{
                    allproducts.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                System.out.println("fail"+t.getMessage());

            }
        });
        return allproducts;
    }
}
