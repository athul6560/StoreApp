package com.azstudio.storeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.azstudio.storeapp.Models.pinVerify;

import com.azstudio.storeapp.Models.responseModel;

import com.azstudio.storeapp.Models.user;
import com.azstudio.storeapp.R;
import com.azstudio.storeapp.Utils.ProgressDialogue;

import com.azstudio.storeapp.ViewModel.AdduserViewModel;
import com.azstudio.storeapp.ViewModel.PinVerifyViewModel;

import com.azstudio.storeapp.ViewModel.regisetrViewModel;

public class Register extends AppCompatActivity {
    EditText name, email, password, confirmpass, pinnumber;
    Button register;
    ProgressDialogue progressDialogue;
    regisetrViewModel regisetrViewModel;
    PinVerifyViewModel PinVerifyViewModel;
    AdduserViewModel adduserViewModel;
    TextView sent, verify;
    user user;
    pinVerify pinVerify;
    boolean pinstatus = false;
    ImageView tick, error, pintick, pinerror;
    ProgressBar sentloading, pinloading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextView login = findViewById(R.id.login);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        pinnumber = findViewById(R.id.pinnumber);
        tick = findViewById(R.id.tick);
        error = findViewById(R.id.error);
        pintick = findViewById(R.id.pintick);
        pinerror = findViewById(R.id.pinerror);
        regisetrViewModel = new regisetrViewModel();
        PinVerifyViewModel = new PinVerifyViewModel();
        adduserViewModel=new AdduserViewModel();
        confirmpass = findViewById(R.id.confirmpassword);
        sent = findViewById(R.id.sent);
        verify = findViewById(R.id.verify);
        sentloading = findViewById(R.id.sentloading);
        pinloading = findViewById(R.id.pinloading);
        progressDialogue = new ProgressDialogue(this);
        register = findViewById(R.id.register);

        register.getBackground().setAlpha(150);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("") || email.getText().toString().equals("") || password.getText().toString().equals("") ||
                        confirmpass.getText().toString().equals("") || pinnumber.getText().toString().equals("")) {
                    Toast.makeText(Register.this, "Please fill all feilds", Toast.LENGTH_SHORT).show();
                } else if (pinstatus) {
                    if(password.getText().toString().equals(confirmpass.getText().toString()))
                    adduser();
                    else
                        Toast.makeText(Register.this, "Check Entered Password", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Register.this, "Pin not verified", Toast.LENGTH_SHORT).show();
                }
            }
        });
        sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().equals("")) {
                    Toast.makeText(Register.this, "Please Enter Email ID", Toast.LENGTH_SHORT).show();
                } else {
                    sentloading.setVisibility(View.VISIBLE);
                    sent.setVisibility(View.GONE);
                    sentemail();
                }
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pinnumber.getText().toString().equals("")) {
                    Toast.makeText(Register.this, "Please Enter Pin", Toast.LENGTH_SHORT).show();
                } else {

                    if (email.getText().toString().equals("")) {
                        Toast.makeText(Register.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    } else {
                        pinloading.setVisibility(View.VISIBLE);
                        verify.setVisibility(View.GONE);
                        pinverify();

                    }
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, profile.class));
                finish();
            }
        });
    }

    private void adduser() {
        if (progressDialogue.isNetworkAvilable(Register.this)) {

            user = new user();
            user.setEmail(email.getText().toString());
            user.setUser_name(name.getText().toString());
            user.setUser_password(password.getText().toString());
            user.setUser_role("USER");
            progressDialogue.show();
            adduserViewModel.adduser(user).observe(Register.this, new Observer<responseModel>() {
                @Override
                public void onChanged(responseModel response) {
                    try {
                        if (response.isStatus()) {

                            progressDialogue.dismiss();
                            Toast.makeText(Register.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Register.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialogue.dismiss();
                        }
                    } catch (Exception e) {
                        progressDialogue.dismiss();
                        Toast.makeText(Register.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        } else {
            sentloading.setVisibility(View.INVISIBLE);
            error.setVisibility(View.VISIBLE);
            Toast.makeText(Register.this, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private void pinverify() {
        if (progressDialogue.isNetworkAvilable(Register.this)) {
            pinVerify = new pinVerify();
            pinVerify.setPin(pinnumber.getText().toString());
            pinVerify.setUser_email(email.getText().toString());
            PinVerifyViewModel.pinverify(pinVerify).observe(Register.this, new Observer<responseModel>() {
                @Override
                public void onChanged(responseModel response) {
                    try {
                        if (response.isStatus()) {
                            pintick.setVisibility(View.VISIBLE);
                            pinloading.setVisibility(View.INVISIBLE);
                            register.getBackground().setAlpha(255);
                            Toast.makeText(Register.this, "Pin Verified", Toast.LENGTH_SHORT).show();
                            pinstatus = true;
                        } else {
                            pinloading.setVisibility(View.INVISIBLE);
                            pinerror.setVisibility(View.VISIBLE);
                            Toast.makeText(Register.this, response.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {
                        pinloading.setVisibility(View.INVISIBLE);
                        pinerror.setVisibility(View.VISIBLE);
                        Toast.makeText(Register.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        } else {
            sentloading.setVisibility(View.INVISIBLE);
            error.setVisibility(View.VISIBLE);
            Toast.makeText(Register.this, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private void sentemail() {
        if (progressDialogue.isNetworkAvilable(Register.this)) {
            regisetrViewModel.getmailresponse(email.getText().toString()).observe(Register.this, new Observer<responseModel>() {
                @Override
                public void onChanged(responseModel response) {
                    try {
                        if (response.isStatus()) {
                            tick.setVisibility(View.VISIBLE);
                            sentloading.setVisibility(View.INVISIBLE);
                        } else {
                            sentloading.setVisibility(View.INVISIBLE);
                            tick.setVisibility(View.VISIBLE);
                            Toast.makeText(Register.this, response.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {
                        sentloading.setVisibility(View.INVISIBLE);
                        error.setVisibility(View.VISIBLE);
                        Toast.makeText(Register.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        } else {
            sentloading.setVisibility(View.INVISIBLE);
            error.setVisibility(View.VISIBLE);
            Toast.makeText(Register.this, "No Network", Toast.LENGTH_SHORT).show();
        }
    }


}
