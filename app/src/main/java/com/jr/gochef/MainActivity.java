package com.jr.gochef;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private int state = 0;
    private int lastItem = 0;


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

}
