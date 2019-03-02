package com.jr.gochef;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import java.io.IOException;
import java.util.Objects;

public class UserFragment extends Fragment {

    private ImageView userPhoto;

    public UserFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Please note the third parameter should be false, otherwise a java.lang.IllegalStateException maybe thrown.
        View mView = inflater.inflate(R.layout.fragment_user, container, false);
        User user = ((MainActivity) Objects.requireNonNull(getContext())).getUser();
        userPhoto = mView.findViewById(R.id.userPhotoView);
        userPhoto.setImageResource(R.drawable.ic_user);
        TextView userName = mView.findViewById(R.id.userNameView);
        userName.setText(user.getName());
        TextView userEmail = mView.findViewById(R.id.userEmail);
        userEmail.setText(user.getEmail());
        Button mButton = mView.findViewById(R.id.userButtonView);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccessToken.isCurrentAccessTokenActive()) {
                    LoginManager.getInstance().logOut();
                } else {
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail()
                            .build();
                    GoogleSignIn.getClient(Objects.requireNonNull(getActivity()), gso).signOut();
                }
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                Objects.requireNonNull(getActivity()).finish();
            }
        });
        new ImageLoader().execute(user.getImage());
        return mView;
    }

    @SuppressLint("StaticFieldLeak")
    public class ImageLoader extends AsyncTask <String, Void, Bitmap>{

        private ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = ProgressDialog.show(getContext(), "", "Loading", true, false);
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap bitmap;
            try {
                bitmap = Helper.downloadDataFromUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
                bitmap = BitmapFactory.decodeResource(Objects.requireNonNull(getContext()).getResources(), R.drawable.ic_404);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute( Bitmap result )  {
            pd.dismiss();
            userPhoto.setImageBitmap(result);
            ((MainActivity) Objects.requireNonNull(getContext())).progressDialog();
        }
    }
}