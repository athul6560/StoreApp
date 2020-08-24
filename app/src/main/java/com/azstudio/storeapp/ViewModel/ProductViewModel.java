package com.azstudio.storeapp.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.azstudio.storeapp.Models.product_outer_model;
import com.azstudio.storeapp.Repository.ProductRepo;

public class ProductViewModel extends ViewModel {
    private MutableLiveData<product_outer_model> mutableLiveData;
    private MutableLiveData<String> count;
    private ProductRepo productRepo;

    public ProductViewModel() {
        productRepo = productRepo.getInstance();

    }

    public LiveData<product_outer_model> getProducts() {

        mutableLiveData = productRepo.getAllProducts();

        return mutableLiveData;
    }

    public LiveData<String> getcartcount(Context context) {

        count = productRepo.getcartcount(context);

        return count;
    }


}