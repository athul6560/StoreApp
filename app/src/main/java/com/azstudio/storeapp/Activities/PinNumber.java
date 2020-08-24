package com.azstudio.storeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.azstudio.storeapp.Models.pinVerify;
import com.azstudio.storeapp.Models.responseModel;
import com.azstudio.storeapp.R;
import com.azstudio.storeapp.Utils.ProgressDialogue;
import com.azstudio.storeapp.Utils.constants;
import com.azstudio.storeapp.ViewModel.PinVerifyViewModel;

public class PinNumber extends AppCompatActivity {
EditText pin;
Button pinsubmit;
    ProgressDialogue progressDialogue;
    PinVerifyViewModel pinVerifyViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_number);
        pin=findViewById(R.id.pinnumber);
        pinsubmit=findViewById(R.id.pinsubmitbtn);
        progressDialogue=new ProgressDialogue(this);
        pinVerifyViewModel=new PinVerifyViewModel();
        if(progressDialogue.isNetworkAvilable(this)){
            pinVerify pinVerify=new pinVerify();
            pinVerify.setPin(pin.getText().toString());
            pinVerify.setUser_email(constants.user.getEmail());
            pinVerifyViewModel.pinverify(pinVerify).observe(PinNumber.this, new Observer<responseModel>() {
                @Override
                public void onChanged(responseModel response) {
                    try {
                        if (response.isStatus()) {
                            startActivity(new Intent(PinNumber.this, MainActivity.class));
                            progressDialogue.dismiss();
                        } else {
                            progressDialogue.dismiss();
                            Toast.makeText(PinNumber.this, response.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }catch (Exception e){
                        progressDialogue.dismiss();
                        Toast.makeText(PinNumber.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }



                }
            });

        }


    }
}
