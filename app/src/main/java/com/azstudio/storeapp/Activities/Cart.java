package com.azstudio.storeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.azstudio.storeapp.Adapter.CartAdapter;
import com.azstudio.storeapp.R;
import com.azstudio.storeapp.dbHandler.DatabaseHandler;

public class Cart extends AppCompatActivity {
    ImageView back;
    private RecyclerView recyclerView;
    DatabaseHandler databaseHandler;
    private ImageView emptycart;
    private Button placeOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        back = findViewById(R.id.back);
        placeOrder = findViewById(R.id.placeorder);
        databaseHandler = new DatabaseHandler(this);
        emptycart = findViewById(R.id.emptycart);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        checkdataempty();
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (databaseHandler.getAllDetails().size() == 0) {
                    emptycart.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    placeOrder.setVisibility(View.GONE);
                } else {
                    startActivity(new Intent(Cart.this, OrderPage.class));
                }

            }
        });
        CartAdapter adapter = new CartAdapter(databaseHandler.getAllDetails(), Cart.this,placeOrder,emptycart);
        // Attach the adapter to the recyclerview to populate items

        recyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void checkdataempty() {
        if (databaseHandler.getAllDetails().size() == 0) {
            emptycart.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            placeOrder.setVisibility(View.GONE);
        }
    }
}
