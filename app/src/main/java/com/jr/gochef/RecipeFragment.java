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
import android.widget.Toast;

import java.util.ArrayList;

public class RecipeFragment extends Fragment implements RecipeListAdapter.ItemClickListener {

    private ImageView topView;
    private TextView topName;
    private TextView topTipe;
    private TextView searchText;
    private ScrollView mScrollView;
    private SearchView mSearchView;
    private RecipeListAdapter adapter;

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
        mScrollView =  mView.findViewById(R.id.recipeScrollView);
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                mScrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        };
        mScrollView.post(runnable);

        //RecycleViewFiller
        ArrayList<Integer> viewColors = new ArrayList<>();
        viewColors.add(Color.BLUE);
        viewColors.add(Color.YELLOW);
        viewColors.add(Color.MAGENTA);
        viewColors.add(Color.RED);
        viewColors.add(Color.BLACK);

        ArrayList<String> recipeNames = new ArrayList<>();
        recipeNames.add("Boiled Water");
        recipeNames.add("Awesome Barbecue");
        recipeNames.add("Dry Camel and gin");
        recipeNames.add("German Black Sheep n®12");
        recipeNames.add("Roasted Goat w/ Honey n' pepper");

        // set up the RecyclerView
        RecyclerView recyclerView = mView.findViewById(R.id.listaReceita);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        adapter = new RecipeListAdapter(getContext(), viewColors, recipeNames);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        return mView;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getContext(), "You clicked " + adapter.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
    }
}
