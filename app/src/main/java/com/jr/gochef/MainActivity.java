package com.jr.gochef;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {

    private static int state = 0;
    private static Recipe mRecipe;
    private Button mButton;
    private String url = "http://www.google.com";
    private User user;
    private ProgressDialog pd;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    progressDialog();
                    state = 0;
                    Fragment recipeFragment = new RecipeFragment();
                    replaceFragment(recipeFragment);
                    return true;
                case R.id.navigation_fav:
                    progressDialog();
                    state = 1;
                    Fragment favFragment = new FavFragment();
                    replaceFragment(favFragment);
                    return true;
                case R.id.navigation_user:
                    progressDialog();
                    state = 0;
                    Fragment userFragment = new UserFragment();
                    replaceFragment(userFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pd = new ProgressDialog(this);
        progressDialog();
        User nUser = (User) getIntent().getSerializableExtra("user");
        user = nUser;
        loadData();
        if(user == null){
            user = nUser;
            saveData();
        }
        BottomNavigationView navigation = findViewById(R.id.navigation);
        mButton = findViewById(R.id.nav_button);
        mButton.setVisibility(View.GONE);
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Fragment mFragment = new RecipeFragment();
        this.replaceFragment(mFragment);
    }

    // Replace current Fragment with the destination Fragment.
    public void replaceFragment(Fragment destFragment) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FragmentLayout, destFragment);
        fragmentTransaction.commit();
    }

    public void reloadFragment() {
        Fragment destFragment = null;
        mButton.setVisibility(View.GONE);
        switch (state){
            case 0:
                destFragment = new RecipeFragment();
                break;
            case 1:
                destFragment = new FavFragment();
                break;
        }
        replaceFragment(destFragment);
    }

    public Recipe getRecipeItem() {
        return mRecipe;
    }

    public void setRecipeItem(Recipe mRecipe) {
        MainActivity.mRecipe = mRecipe;
        url = mRecipe.imageUrl;
    }

    public User getUser(){
        return  user;
    }

    public void setUser(User user){
        this.user = user;
        saveData();
    }

    public void showButton(){
        mButton.setVisibility(View.VISIBLE);
    }

    public void loadData(){
            try {
                FileInputStream fis = this.openFileInput(user.getEmail());
                try {
                    ObjectInputStream is = new ObjectInputStream(fis);
                    try {
                        user = (User) is.readObject();
                        is.close();
                        fis.close();
                    } catch (ClassNotFoundException e) {




                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                user = null;
            }
    }

    public void saveData(){
        try{
            FileOutputStream fos = this.openFileOutput(user.getEmail(), Context.MODE_PRIVATE);
            try{
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(user);
                os.close();
                fos.close();
            }catch(IOException e) {
                e.printStackTrace();
            }
        }catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void progressDialog(){
        if(pd.isShowing()){
            pd.dismiss();
        } else {
            pd.show();
        }
    }

}
