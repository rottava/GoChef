package com.jr.gochef;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RecipeFragment extends Fragment{

    private ImageView topView;
    private TextView topName;
    private TextView topType;
    private TextView searchText;
    private ScrollView mScrollView;
    private SearchView mSearchView;
    private RecycleListAdapter adapter;
    private RecyclerView mRecyclerView;
    private ArrayList<Recipe> recipes;
    private String firstRecipe = "meat";

    public RecipeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Please note the third parameter should be false, otherwise a java.lang.IllegalStateException maybe thrown.
        View mView = inflater.inflate(R.layout.fragment_recipe, container, false);
        mRecyclerView = mView.findViewById(R.id.listaReceita);
        topView = mView.findViewById(R.id.topImageView);
        topView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((MainActivity)getActivity()).showButton();
                ((MainActivity)getActivity()).setLastItem(0);
                ((MainActivity)getActivity()).replaceFragment(new ExpFragment());
            }
        });
        topName = mView.findViewById(R.id.topRecipeName);
        topType = mView.findViewById(R.id.topRecipeType);
        mSearchView = mView.findViewById(R.id.searchView);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Bundle bundle=new Bundle();
                bundle.putString("search", mSearchView.getQuery().toString());
                SearchFragment fragment = new SearchFragment();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.FragmentLayout, fragment);
                fragmentTransaction.commit();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Log.d(TAG, "Query: " + newText);
                return false;
            }
        });
        searchText = mView.findViewById(R.id.searchViewText);
        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchView.setIconified(false);
            }
        });

        fillRecipes();

        if(recipes == null || recipes.size() == 0){
            fillRecipesTest();
        }

        topName.setText(recipes.get(0).getRecipeName());
        topType.setText(recipes.get(0).getAttributes());

        try{
            URL url = new URL(recipes.get(0).getImageUrl());
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            topView.setImageBitmap(bmp);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        ((MainActivity)getActivity()).setRecipeItem(recipes.get(0));
        recipes.remove(recipes.get(0));

        return mView;
    }

    private void fillRecipes(){
        final YummlyService yummlyService = new YummlyService();
        yummlyService.findRecipes(firstRecipe, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response)  {
                recipes = yummlyService.processResults(response);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                        adapter = new RecycleListAdapter(getContext(), recipes);
                        mRecyclerView.setAdapter(adapter);
                    }
                });
            }
        });
    }

    private void fillRecipesTest(){
        //RecycleViewFiller
        recipes = new ArrayList<>();
        Recipe recipe = new Recipe();
        recipe.setImageUrl(Integer.toString(Color.BLUE));
        recipe.setRecipeName("Boiled Water");
        recipe.setAttributes("Drink");
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("Water");
        recipe.setIngredients(ingredients);
        ArrayList<String> steps = new ArrayList<>();
        steps.add("Boil the water");
        recipe.setSteps(steps);
        recipes.add(recipe);
        recipe = new Recipe();
        recipe.setImageUrl(Integer.toString(Color.YELLOW));
        recipe.setRecipeName("Awesome Barbecue");
        recipe.setIngredients(ingredients);
        recipe.setSteps(steps);
        recipes.add(recipe);
        recipe = new Recipe();
        recipe.setImageUrl(Integer.toString(Color.MAGENTA));
        recipe.setRecipeName("Dry Camel and gin");
        recipe.setIngredients(ingredients);
        recipe.setSteps(steps);
        recipes.add(recipe);
        recipe = new Recipe();
        recipe.setImageUrl(Integer.toString(Color.RED));
        recipe.setRecipeName("German Black Sheep n®12");
        recipe.setIngredients(ingredients);
        recipe.setSteps(steps);
        recipes.add(recipe);
        recipe = new Recipe();
        recipe.setImageUrl(Integer.toString(Color.BLACK));
        recipe.setRecipeName("Roasted Goat w/ Honey n' pepper");
        recipe.setIngredients(ingredients);
        recipe.setSteps(steps);
        recipes.add(recipe);
    }//
}
