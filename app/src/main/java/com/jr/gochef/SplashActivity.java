package com.jr.gochef;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

    private GoogleSignInClient googleSignInClient;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        Runnable progressRunnable = new Runnable() {
            @Override
            public void run() {
                checkLogin();
            }
        };
        Handler timeout = new Handler();
        timeout.postDelayed(progressRunnable, 2000);

    }

    private void checkLogin(){
        if(AccessToken.getCurrentAccessToken() != null){
            if(AccessToken.isCurrentAccessTokenActive()) {
                loginFacebook();
            } else {
                LoginManager.getInstance().logOut();
            }
        } else {
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            if(account != null){
                googleSignInClient.silentSignIn().addOnSuccessListener(new OnSuccessListener<GoogleSignInAccount>() {
                    @Override
                    public void onSuccess(GoogleSignInAccount googleSignInAccount) {
                        loginGoogle(googleSignInAccount);
                    }
                });
            } else {
                toLogin();
            }
        }
    }

    private void loginGoogle(GoogleSignInAccount googleUser){
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        user = new User();
        user.setName(googleUser.getDisplayName());
        user.setEmail(googleUser.getEmail());
        user.setId(googleUser.getId());
        user.setImage(Objects.requireNonNull(googleUser.getPhotoUrl()).toString());
        user.setFavorites(new ArrayList<Recipe>());
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    private void loginFacebook(){
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    private void toLogin(){
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
