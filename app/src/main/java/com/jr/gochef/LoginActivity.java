package com.jr.gochef;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private Integer G_SIGN_IN = 9001;
    private CallbackManager callbackManager;
    private GoogleSignInClient googleSignInClient;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Facebook Login
        LoginButton buttonFacebook = findViewById(R.id.button_login_facebook);
        buttonFacebook.setReadPermissions(Arrays.asList("public_profile", "email"));
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject me, GraphResponse response) {
                                if (response.getError() != null) {
                                    Log.w(TAG, "Get session data " + response.getRawResponse());
                                } else {
                                    Log.i(TAG, "Facebook signIn Result: successfully");
                                    try{
                                        user.setName(me.getString("name"));
                                        user.setEmail(me.getString("email"));
                                        user.setId(me.getString("id"));
                                        try{
                                            URL url = new URL("https://graph.facebook.com/" + me.getString("id") + "/picture?type=large");
                                            user.setImage(url.toString());
                                        }catch (MalformedURLException e){
                                            e.printStackTrace();
                                        }
                                        user.setFavorites(new ArrayList<Recipe>());
                                    } catch (JSONException e){
                                        e.printStackTrace();
                                    }
                                    loginFacebook();
                                }
                            }
                        }).executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Log.i(TAG, "Facebook signIn Result: Canceled");
                    }

                    @Override
                    public void onError(FacebookException e) {
                        Log.w(TAG, "Facebook signIn Result: " + e.getMessage());
                    }
                });
        //Google Login
        SignInButton buttonGoogle = findViewById(R.id.button_login_google);
        buttonGoogle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button_login_google:
                        signIn();
                        break;
                }
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    /*/
    @Override
    protected void onResume() {
        super.onResume();
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
            }
        }
    }
    /*/

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, G_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == G_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.i(TAG, "Google signIn Result: successfully");
            if(account != null){
                loginGoogle(account);
            }
        } catch (ApiException e) {
            Log.w(TAG,"Google signIn result: failed code=" + e.getStatusCode());
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

}
