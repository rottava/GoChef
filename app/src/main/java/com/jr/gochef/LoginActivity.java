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

import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private Integer G_SIGN_IN = 9001;

    private LoginButton buttonFacebook;
    private CallbackManager callbackManager;

    private SignInButton buttonGoogle;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Facebook Login
        buttonFacebook = findViewById(R.id.button_login_facebook);
        buttonFacebook.setReadPermissions(Arrays.asList("public_profile", "email"));
        // Callback registration
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
        buttonGoogle = findViewById(R.id.button_login_google);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(AccessToken.isCurrentAccessTokenActive()) {
            LoginManager.getInstance().logOut();
        } else {
            googleSignInClient.signOut();
        }
    }


    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, G_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == G_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
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
            // Signed in successfully, show authenticated UI.
            Log.i(TAG, "Google signIn Result: successfully");
            if(account != null){
                loginGoogle(account);
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG,"Google signIn result: failed code=" + e.getStatusCode());
            //loginGoogle(null);
        }
    }

    private void loginGoogle(GoogleSignInAccount googleUser){
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        String dataLog = "G" + googleUser.getId();
        intent.putExtra("datalog", dataLog);
        startActivity(intent);
        finish();
    }

    private void loginFacebook(){
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        String dataLog = "F" + AccessToken.getCurrentAccessToken().getUserId();
        intent.putExtra("datalog", dataLog);
        startActivity(intent);
        finish();
    }

}
