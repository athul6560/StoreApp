package com.azstudio.storeapp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.azstudio.storeapp.Models.orderDetails;

import com.azstudio.storeapp.Repository.OrderRepo;

import java.util.List;

public class OrderViewModel extends ViewModel {
    private MutableLiveData<List<orderDetails>> mutableLiveData;

    private OrderRepo orderRepo;

    public OrderViewModel() {

        orderRepo= orderRepo.getInstance();
    }
    public LiveData<List<orderDetails>> getorder(String email) {

        mutableLiveData = orderRepo.getorder(email);

        return mutableLiveData;
    }
    public LiveData<List<orderDetails>> addorder(List<orderDetails> order) {

        mutableLiveData = orderRepo.addorder(order);

        return mutableLiveData;
    }
    public void sentsms(String number,String product) {

         orderRepo.sentsms(number,product);


    }
}
