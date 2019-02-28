package com.jr.gochef;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static int state = 0;
    private static int lastItem = 0;
    private static Recipe mRecipe;
    private Button mButton;
    private String url = "http://www.google.com";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    state = 0;
                    Fragment recipeFragment = new RecipeFragment();
                    replaceFragment(recipeFragment);
                    return true;
                case R.id.navigation_fav:
                    state = 1;
                    Fragment favFragment = new FavFragment();
                    replaceFragment(favFragment);
                    return true;
                /*case R.id.navigation_user:
                    state = 2;
                    Fragment userFragment = new RecipeFragment();
                    replaceFragment(userFragment);
                    return true;

                 */
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "ID: " + getIntent().getStringExtra("datalog"));

        BottomNavigationView navigation = findViewById(R.id.navigation);
        mButton = findViewById(R.id.nav_button);
        mButton.setVisibility(View.GONE);
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                //i.setData(Uri.parse(recycleListItem.getUrl()));
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
            /*case 2:
                destFragment = new userFragment();
                break;*/
        }
        replaceFragment(destFragment);
    }

    public int getState(){
        return state;
    }

    public int getLastItem(){
        return lastItem;
    }

    public void setLastItem(int item){
        lastItem = item;
    }

    public Recipe getRecipeItem() {
        return mRecipe;
    }

    public void setRecipeItem(Recipe mRecipe) {
        this.mRecipe = mRecipe;
        url = mRecipe.imageUrl;
    }

    public void showButton(){
        mButton.setVisibility(View.VISIBLE);
    }

}
