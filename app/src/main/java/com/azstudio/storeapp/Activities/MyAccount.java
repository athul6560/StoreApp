package com.azstudio.storeapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.azstudio.storeapp.Adapter.ProductListAdapter;
import com.azstudio.storeapp.Adapter.orderlistadapter;
import com.azstudio.storeapp.Models.orderDetails;
import com.azstudio.storeapp.R;
import com.azstudio.storeapp.Utils.ProgressDialogue;
import com.azstudio.storeapp.Utils.constants;
import com.azstudio.storeapp.ViewModel.OrderViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAccount extends AppCompatActivity {
    Button logout;
    GoogleSignInClient mGoogleSignInClient;
    TextView name, email;
    ImageView profilepic, back;
    public static String EMAIL = "";
    private RecyclerView recyclerView;
    private OrderViewModel orderViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        profilepic = findViewById(R.id.imageprofile);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        back = findViewById(R.id.back);
        orderViewModel=new OrderViewModel();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = LayoutInflater.from(MyAccount.this);
                final View views = inflater.inflate(R.layout.areyousure_popuptwo, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(MyAccount.this);
                builder.setView(views);
                TextView name = (TextView) views.findViewById(R.id.suretetxt);
                Button confirm = (Button) views.findViewById(R.id.confirmbtn);
                Button cancel = (Button) views.findViewById(R.id.cancelbtn);
                builder.setCancelable(true);
                final AlertDialog alert = builder.create();
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                alert.show();
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        signout();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }
                });

            }
        });

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            EMAIL = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            if(personPhoto!=null)
            Picasso.get().load(personPhoto).into(profilepic);
            name.setText(personName);
            email.setText(personEmail);
        }

        orderViewModel.getorder(MyAccount.EMAIL).observe(MyAccount.this, new Observer<List<orderDetails>>() {
            @Override
            public void onChanged(List<orderDetails> response) {

                try {
                    if(ProgressDialogue.isNetworkAvilable(MyAccount.this)){
                    orderlistadapter adapter = new orderlistadapter(response, MyAccount.this);
                    // Attach the adapter to the recyclerview to populate items
                    recyclerView.setAdapter(adapter);
                    // Set layout manager to position the items
                    recyclerView.setLayoutManager(new LinearLayoutManager(MyAccount.this));}else{
                        Toast.makeText(MyAccount.this, "No Network", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(MyAccount.this, "Server Error"+e, Toast.LENGTH_SHORT).show();

                }

            }
        });
    }


    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        super.onStart();
    }



    private void signout() {

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        SharedPreferences settings = getSharedPreferences(constants.PREFS_NAME, 0); // 0 - for private mode
                        SharedPreferences.Editor editor = settings.edit();

//Set "hasLoggedIn" to true
                        editor.putBoolean("hasLoggedIn", false);

// Commit the edits!
                        editor.commit();
                        Toast.makeText(MyAccount.this, "Logged out", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}
