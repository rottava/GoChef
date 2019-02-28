package com.jr.gochef;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeFragment extends Fragment implements RecycleListAdapter.ItemClickListener {

    private ImageView topView;
    private TextView topName;
    private TextView topTipe;
    private TextView searchText;
    private ScrollView mScrollView;
    private SearchView mSearchView;
    private RecycleListAdapter adapter;

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
        topView = mView.findViewById(R.id.topImageView);
        topView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((MainActivity)getActivity()).showButton();
                ((MainActivity)getActivity()).setLastItem(0);
                ((MainActivity)getActivity()).replaceFragment(new ExpFragment());
            }
        });
        topName = mView.findViewById(R.id.topRecipeName);
        topTipe = mView.findViewById(R.id.topRecipeType);
        mSearchView = mView.findViewById(R.id.searchView);
        searchText = mView.findViewById(R.id.searchViewText);
        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchView.setIconified(false);
            }
        });

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

        topName.setText(recipes.get(0).getName());
        //topTipe.setText(recipes.get(0).getName());
        topView.setBackgroundColor(recipes.get(0).getImage());
        ((MainActivity)getActivity()).setRecycleListItem(recipes.get(0));
        recipes.remove(recipes.get(0));

        // set up the RecyclerView
        RecyclerView recyclerView = mView.findViewById(R.id.listaReceita);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
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
