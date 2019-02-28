package com.jr.gochef;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class FavFragment extends Fragment implements RecycleListAdapter.ItemClickListener {
    
    private TextView mTextView;
    private ScrollView mScrollView;
    private RecycleListAdapter adapter;

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

        //RecycleViewFiller
        ArrayList<RecycleListItem> recipes = new ArrayList<>();
        RecycleListItem recipe = new RecycleListItem();
        recipe.setImage(Color.BLUE);
        recipe.setName("Boiled Water");
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
        recipe = new RecycleListItem();
        recipe.setImage(Color.YELLOW);
        recipe.setName("Awesome Barbecue");
        recipe.setIngredients(ingredients);
        recipe.setSteps(steps);
        recipes.add(recipe);
        recipe = new RecycleListItem();
        recipe.setImage(Color.MAGENTA);
        recipe.setName("Dry Camel and gin");
        recipe.setIngredients(ingredients);
        recipe.setSteps(steps);
        recipes.add(recipe);
        recipe = new RecycleListItem();
        recipe.setImage(Color.RED);
        recipe.setName("German Black Sheep n®12");
        recipe.setIngredients(ingredients);
        recipe.setSteps(steps);
        recipes.add(recipe);
        recipe = new RecycleListItem();
        recipe.setImage(Color.BLACK);
        recipe.setName("Roasted Goat w/ Honey n' pepper");
        recipe.setIngredients(ingredients);
        recipe.setSteps(steps);
        recipes.add(recipe);

        // set up the RecyclerView
        RecyclerView recyclerView = mView.findViewById(R.id.listaReceita);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new RecycleListAdapter(getContext(), recipes);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        return mView;
    }

    @Override
    public void onItemClick(View view, int position, RecycleListItem recycleListItem) {
        ((MainActivity)getActivity()).showButton();
        ((MainActivity)getActivity()).setLastItem(position);
        ((MainActivity)getActivity()).setRecycleListItem(recycleListItem);
        ((MainActivity)getActivity()).replaceFragment(new ExpFragment());
    }

}