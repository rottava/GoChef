package com.jr.gochef;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class FavFragment extends Fragment implements RecycleListAdapter.ItemClickListener {
    
    private TextView mTextView;
    private ScrollView mScrollView;
    private RecyclerView mRecyclerView;
    private RecycleListAdapter adapter;
    ArrayList<Recipe> recipes;

    public FavFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Please note the third parameter should be false, otherwise a java.lang.IllegalStateException maybe thrown.
        View mView = inflater.inflate(R.layout.fragment_fav, container, false);
        mTextView = mView.findViewById(R.id.favText);
        fillRecipesTest();
        // set up the RecyclerView
        mRecyclerView = mView.findViewById(R.id.listaReceita);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        adapter = new RecycleListAdapter(getContext(), recipes);
        mRecyclerView.setAdapter(adapter);

        return mView;
    }

    @Override
    public void onItemClick(View view, int position, Recipe recycleListItem) {
        ((MainActivity)getActivity()).showButton();
        ((MainActivity)getActivity()).setLastItem(position);
        ((MainActivity)getActivity()).setRecipeItem(recycleListItem);
        ((MainActivity)getActivity()).replaceFragment(new ExpFragment());
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