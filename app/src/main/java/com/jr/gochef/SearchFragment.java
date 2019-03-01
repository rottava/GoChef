package com.jr.gochef;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SearchFragment extends Fragment {

    private TextView searchType;
    private ImageView searchBack;
    private RecycleListAdapter adapter;
    private RecyclerView mRecyclerView;
    private ArrayList<Recipe> recipes;
    private String search;

    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Please note the third parameter should be false, otherwise a java.lang.IllegalStateException maybe thrown.
        View mView = inflater.inflate(R.layout.fragment_search, container, false);
        search = getArguments().getString("search");
        mRecyclerView = mView.findViewById(R.id.searchList);
        searchType = mView.findViewById(R.id.searchText);
        searchType.setText(search);
        searchBack = mView.findViewById(R.id.searchView);
        searchBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.FragmentLayout, new RecipeFragment());
                fragmentTransaction.commit();
            }
        });

        fillRecipes();

        return mView;

    }

    private void fillRecipes(){
        final YummlyService yummlyService = new YummlyService();
        yummlyService.findRecipes(search, new Callback() {
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
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                        adapter = new RecycleListAdapter(getContext(), recipes);
                        mRecyclerView.setAdapter(adapter);
                    }
                });
            }
        });
    }


    //
    private void fillRecipesTest(){
        //RecycleViewFiller
        recipes = new ArrayList<>();
        Recipe recipe = new Recipe();
        recipe.setImageUrl(Integer.toString(Color.BLUE));
        recipe.setRecipeName("Boiled Water");
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("Water");
        ingredients.add("Water");
        ingredients.add("Water");
        ingredients.add("Water");
        ingredients.add("Water");
        recipe.setIngredients(ingredients);
        ArrayList<String> steps = new ArrayList<>();
        steps.add("Boil the water");
        steps.add("Boil the water");
        steps.add("Boil the water");
        steps.add("Boil the water");
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