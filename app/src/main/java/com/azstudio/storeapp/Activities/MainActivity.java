package com.azstudio.storeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.azstudio.storeapp.Adapter.ProductListAdapter;
import com.azstudio.storeapp.Models.cartModel;
import com.azstudio.storeapp.Models.product_outer_model;
import com.azstudio.storeapp.R;
import com.azstudio.storeapp.Utils.ProgressBar;
import com.azstudio.storeapp.Utils.ProgressDialogue;
import com.azstudio.storeapp.Utils.constants;
import com.azstudio.storeapp.ViewModel.ProductViewModel;
import com.azstudio.storeapp.dbHandler.DatabaseHandler;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    ProgressDialogue progressDialogue;
    private ProgressBar progress;
    ImageView cart;
    ProductViewModel productViewModel;
    private RelativeLayout dot;
    private TextView dottext;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout nonet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dot = findViewById(R.id.dot);
        dottext = findViewById(R.id.dottext);
        productViewModel = new ProductViewModel();
        progressDialogue = new ProgressDialogue(this);
        cart = findViewById(R.id.cart);
        nonet = findViewById(R.id.nonet);
        swipeRefreshLayout = findViewById(R.id.swipelayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        final ImageView profile = findViewById(R.id.profile);

        final DatabaseHandler db = new DatabaseHandler(this);
        setDotnumberCart(productViewModel);
        Log.d("Reading: ", "Reading all Details..");
        List<cartModel> contacts = db.getAllDetails();

        for (cartModel cn : contacts) {
            String log = "Id: " + cn.getId() + " ,Type: " + cn.getName() + " ,Price: " +
                    cn.getPrice() + " Status : " + cn.getPrice();

            Log.d("Name: ", log);
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (ProgressDialogue.isNetworkAvilable(MainActivity.this)) {
                    nonet.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    productViewModel.getProducts().observe(MainActivity.this, new Observer<product_outer_model>() {
                        @Override
                        public void onChanged(product_outer_model response) {

                            try {
                                SharedPreferences settings = getSharedPreferences(constants.PREFS_NAME, 0);

                                ProductListAdapter adapter = new ProductListAdapter(response.get_embedded(), MainActivity.this, dot, dottext, settings);
                                // Attach the adapter to the recyclerview to populate items
                                recyclerView.setAdapter(adapter);
                                // Set layout manager to position the items
                                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                swipeRefreshLayout.setRefreshing(false);
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, "Server Error", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
                } else {
                    recyclerView.setVisibility(View.GONE);
                    nonet.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "No Network", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Cart.class));

            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings = getSharedPreferences(constants.PREFS_NAME, 0);
                boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);
                if (hasLoggedIn) {
                    startActivity(new Intent(MainActivity.this, MyAccount.class));
                } else {
                    startActivity(new Intent(MainActivity.this, profile.class));
                }
            }
        });
        if (
                progressDialogue.isNetworkAvilable(this)
        ) {
            progress = ProgressBar.show(MainActivity.this, "#358ac0", "");
            nonet.setVisibility(View.GONE);

            productViewModel.getProducts().observe(this, new Observer<product_outer_model>() {
                @Override
                public void onChanged(product_outer_model response) {

                    try {
                        SharedPreferences settings = getSharedPreferences(constants.PREFS_NAME, 0);

                        ProductListAdapter adapter = new ProductListAdapter(response.get_embedded(), MainActivity.this, dot, dottext, settings);
                        // Attach the adapter to the recyclerview to populate items
                        recyclerView.setAdapter(adapter);
                        // Set layout manager to position the items
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        progress.dismiss();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }

                }
            });
        } else {
            nonet.setVisibility(View.VISIBLE);
            Toast.makeText(this, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private void setDotnumberCart(ProductViewModel productViewModel) {
        productViewModel.getcartcount(MainActivity.this).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String response) {

                if (!response.equals("0")) {
                    dot.setVisibility(View.VISIBLE);
                    dottext.setText(response);
                }

            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        productViewModel.getcartcount(MainActivity.this).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String response) {

                if (!response.equals("0")) {
                    dot.setVisibility(View.VISIBLE);
                    dottext.setText(response);
                } else {
                    dot.setVisibility(View.INVISIBLE);
                }

            }
        });
    }
}
