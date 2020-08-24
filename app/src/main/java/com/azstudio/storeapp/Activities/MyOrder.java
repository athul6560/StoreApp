package com.azstudio.storeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.widget.Toast;

import com.azstudio.storeapp.Models.orderDetails;
import com.azstudio.storeapp.R;
import com.azstudio.storeapp.ViewModel.OrderViewModel;

import java.util.List;

public class MyOrder extends AppCompatActivity {
   private OrderViewModel orderViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        orderViewModel=new OrderViewModel();
        orderViewModel.getorder(MyAccount.EMAIL).observe(MyOrder.this, new Observer<List<orderDetails>>() {
            @Override
            public void onChanged(List<orderDetails> response) {

                try {
                    Toast.makeText(MyOrder.this, ""+response.get(1).getOrder_status(), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(MyOrder.this, "Server Error"+e, Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
}