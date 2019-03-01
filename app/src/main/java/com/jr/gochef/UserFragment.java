package com.jr.gochef;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class UserFragment extends Fragment {

    private ImageView userPhoto;
    private TextView userName;
    private Button mButton;
    private User user;

    public UserFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Please note the third parameter should be false, otherwise a java.lang.IllegalStateException maybe thrown.
        View mView = inflater.inflate(R.layout.fragment_user, container, false);
        user = ((MainActivity)getContext()).getUser();
        userPhoto = mView.findViewById(R.id.userPhotoView);
        if(user.getType()){
            URL url = null;
            try{
                try{
                    url = new URL(user.getImage());
                }catch (MalformedURLException e){
                    e.printStackTrace();
                }
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                userPhoto.setImageBitmap(bmp);
            }catch (IOException e){
                e.printStackTrace();
            }
        } else {
            if(!user.getImage().equals("")){
                    userPhoto.setImageURI(Uri.parse(user.getImage()));
            }
        }
        if(userPhoto.getDrawable() == null) {
            userPhoto.setImageResource(R.drawable.ic_user);
        }
        userName = mView.findViewById(R.id.userNameView);
        userName.setText(user.getName());
        mButton = mView.findViewById(R.id.userButtonView);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AccessToken.isCurrentAccessTokenActive()) {
                    LoginManager.getInstance().logOut();
                } else {
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail()
                            .build();
                    GoogleSignIn.getClient(getActivity(), gso).signOut();
                }
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return mView;
    }
}
