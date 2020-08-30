package com.azstudio.storeapp.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.azstudio.storeapp.Models.responseModel;
import com.azstudio.storeapp.Models.user;
import com.azstudio.storeapp.R;
import com.azstudio.storeapp.Utils.ProgressDialogue;
import com.azstudio.storeapp.Utils.constants;
import com.azstudio.storeapp.ViewModel.AdduserViewModel;
import com.azstudio.storeapp.ViewModel.LoginViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class profile extends AppCompatActivity {
    private static final String TAG = "profileactivity";
    LoginViewModel loginViewModel;
    ProgressDialogue progressDialogue;
    EditText email, password;
    GoogleSignInClient mGoogleSighnInClient;
    SignInButton signInButton;
    AdduserViewModel adduserViewModel;
    public static int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        email = findViewById(R.id.emailid);
        password = findViewById(R.id.password);
        signInButton = findViewById(R.id.googlesignin);
        adduserViewModel = new AdduserViewModel();
        loginViewModel = new LoginViewModel();
        progressDialogue = new ProgressDialogue(this);
        progressDialogue.show();
        Button login = findViewById(R.id.login);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSighnInClient = GoogleSignIn.getClient(this, gso);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSighnInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Toast.makeText(profile.this, "Please Enter Username/Password", Toast.LENGTH_SHORT).show();
                } else {
                    login();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void updateUI(GoogleSignInAccount account) {

        if (account != null) {
            SharedPreferences settings = getSharedPreferences(constants.PREFS_NAME, 0); // 0 - for private mode
            SharedPreferences.Editor editor = settings.edit();

//Set "hasLoggedIn" to true
            editor.putBoolean("hasLoggedIn", true);

// Commit the edits!
            editor.commit();
            if (progressDialogue.isNetworkAvilable(profile.this)) {
                user user = new user();
                user.setUser_name(account.getDisplayName());
                user.setEmail(account.getEmail());
                user.setUser_password(account.getId() + "");
                user.setUser_role("USER");
                adduserViewModel.adduser(user).observe(profile.this, new Observer<responseModel>() {
                    @Override
                    public void onChanged(responseModel response) {

                        try {
                            if (response.isStatus()) {

                                progressDialogue.dismiss();
                                Toast.makeText(profile.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(profile.this, MyAccount.class));
                                finish();
                            } else {
                                progressDialogue.dismiss();
                                startActivity(new Intent(profile.this, MyAccount.class));
                                finish();
                                //  Toast.makeText(profile.this, response.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        } catch (Exception e) {
                            progressDialogue.dismiss();
                            Toast.makeText(profile.this, "Server Error", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

            } else {
                progressDialogue.dismiss();
                Toast.makeText(this, "No Network", Toast.LENGTH_SHORT).show();
            }
        } else {
            progressDialogue.dismiss();
            // Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void login() {
        if (progressDialogue.isNetworkAvilable(profile.this)) {
            progressDialogue.show();
            loginViewModel.login(email.getText().toString(), password.getText().toString()).observe(profile.this, new Observer<responseModel>() {
                @Override
                public void onChanged(responseModel response) {

                    if (response.isStatus()) {

                        progressDialogue.dismiss();
                        Toast.makeText(profile.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(profile.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialogue.dismiss();
                    }


                }
            });
        } else {
            Toast.makeText(this, "No Network", Toast.LENGTH_SHORT).show();
        }
    }
}
