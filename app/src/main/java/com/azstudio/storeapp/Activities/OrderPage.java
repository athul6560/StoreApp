package com.azstudio.storeapp.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.azstudio.storeapp.Models.cartModel;
import com.azstudio.storeapp.Models.orderDetails;
import com.azstudio.storeapp.R;
import com.azstudio.storeapp.ViewModel.OrderViewModel;
import com.azstudio.storeapp.dbHandler.DatabaseHandler;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;
import java.util.List;

public class OrderPage extends AppCompatActivity {


    private TextView totalorder, emamil;

    private DatabaseHandler databaseHandler;
    private Button place;

    private EditText houseName, street, city, pinCode, postOffice, phoneNumber;
    private OrderViewModel orderViewModel;
    private String personEmail, personName;
    List<orderDetails> order;
    private String personId;
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);

        houseName = findViewById(R.id.housename);
        street = findViewById(R.id.town);
        city = findViewById(R.id.city);
        pinCode = findViewById(R.id.pincode);
        postOffice = findViewById(R.id.postoffice);
        phoneNumber = findViewById(R.id.phonenumber);
        place = findViewById(R.id.placefinal);
        checkuser();
        order = new ArrayList<>();
        //  setdata();
        Context context = this;
        sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        String housename = sharedPref.getString("housename", "");
        String streets = sharedPref.getString("street", "");
        String citys = sharedPref.getString("city", "");
        String pincode = sharedPref.getString("pincode", "");
        String postoffice = sharedPref.getString("postoffice", "");
        String phonenumber = sharedPref.getString("phonenumber", "");
        if(housename.equals("") ||
        streets.equals("") ||
                citys.equals("") ||
                pincode.equals("") ||
                postoffice.equals("") ||
                phonenumber.equals("")){

        }else {
            houseName.setText(housename);
            street.setText(streets);
            city.setText(citys);
            pinCode.setText(pincode);
            postOffice.setText(postoffice);
            phoneNumber.setText(phonenumber);

        }
        orderViewModel = new OrderViewModel();
        totalorder = findViewById(R.id.totalitem);
        emamil = findViewById(R.id.email);
        databaseHandler = new DatabaseHandler(this);
        totalorder.setText("Total Item: " + databaseHandler.getAllDetails().size());
        emamil.setText("Email ID: " + personEmail);
        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (houseName.getText().toString().equals("") ||
                        street.getText().toString().equals("") ||
                        city.getText().toString().equals("") ||
                        pinCode.getText().toString().equals("") ||
                        postOffice.getText().toString().equals("") ||
                        phoneNumber.getText().toString().equals("")
                ) {
                    Toast.makeText(OrderPage.this, "PLease fill all Details", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("housename", houseName.getText().toString());
                    editor.putString("street", street.getText().toString());
                    editor.putString("city",  city.getText().toString());
                    editor.putString("pincode",  pinCode.getText().toString());
                    editor.putString("postoffice",  postOffice.getText().toString());
                    editor.putString("phonenumber",   phoneNumber.getText().toString());
                    editor.commit();
                    String address = personName + "\n" + houseName.getText().toString() + "\n" + street.getText().toString() + "\n" + city.getText().toString() + "\n"  + postOffice.getText().toString() + "\n" + pinCode.getText().toString() + "\n" + "Ph: " + phoneNumber.getText().toString();
                    List<cartModel> items = databaseHandler.getAllDetails();
                    for (int i = 0; i < items.size(); i++) {
                        orderDetails OrderDetails = new orderDetails();
                        OrderDetails.setAddress(address);
                        OrderDetails.setEmail(personEmail);
                        try {
                            int ji = Integer.parseInt(personId);
                            OrderDetails.setUser_id(ji);
                        } catch (NumberFormatException ex) { // handle your exception
                            OrderDetails.setUser_id(1);
                        }

                        OrderDetails.setProduct_id(items.get(i).getProductid());
                        OrderDetails.setPin_number(items.get(i).getSize());
                        OrderDetails.setPhone_number(phoneNumber.getText().toString());
                        OrderDetails.setOrder_status("pending");
                        order.add(OrderDetails);
                    }
                    showpopup(address);
                }
            }
        });

    }

    private void showpopup(String address) {

        LayoutInflater inflater = LayoutInflater.from(OrderPage.this);
        final View views = inflater.inflate(R.layout.areyousure_popuptwo, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(OrderPage.this);
        builder.setView(views);
        TextView name = (TextView) views.findViewById(R.id.suretetxt);
        Button confirm = (Button) views.findViewById(R.id.confirmbtn);
        Button cancel = (Button) views.findViewById(R.id.cancelbtn);
        name.setText(address);
        builder.setCancelable(true);
        final AlertDialog alert = builder.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        alert.show();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderViewModel.addorder(order).observe(OrderPage.this, new Observer<List<orderDetails>>() {
                    @Override
                    public void onChanged(List<orderDetails> response) {

                        try {
                            Toast.makeText(OrderPage.this, "success", Toast.LENGTH_SHORT).show();
                            databaseHandler.deleteAll();
                            Intent i = new Intent(OrderPage.this, MainActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            finish();

                        } catch (Exception e) {
                            Toast.makeText(OrderPage.this, "Server Error" + e, Toast.LENGTH_SHORT).show();

                        }

                    }
                });
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.clear();
                alert.dismiss();
            }
        });



    }


    private void checkuser() {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            personEmail = acct.getEmail();

            personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

        } else {
            Toast.makeText(this, "Please Login", Toast.LENGTH_SHORT).show();
        }
    }
}
