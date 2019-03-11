package com.jr.gochef;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.SearchView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RecipeFragment extends Fragment{

    private ImageView topView;
    private TextView topName;
    private TextView topType;
    private SearchView mSearchView;
    private RecycleListAdapter adapter;
    private RecyclerView mRecyclerView;
    private ArrayList<Recipe> recipes;
    private Recipe topRecipe;

    public RecipeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Please note the third parameter should be false, otherwise a java.lang.IllegalStateException maybe thrown.
        View mView = inflater.inflate(R.layout.fragment_recipe, container, false);
        mRecyclerView = mView.findViewById(R.id.listaReceita);
        topView = mView.findViewById(R.id.topImageView);
        topView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((MainActivity) Objects.requireNonNull(getActivity())).progressDialog();
                ((MainActivity) Objects.requireNonNull(getActivity())).showButton();
                ((MainActivity)getActivity()).setRecipeItem(topRecipe);
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
                FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
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
        TextView searchText = mView.findViewById(R.id.searchViewText);
        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchView.setIconified(false);
            }
        });

        fillRecipes();

        if(recipes == null || recipes.size() == 0){
            fillRecipesTest();
            topRecipe = recipes.get(0);
            recipes.remove(recipes.get(0));
            ((MainActivity) Objects.requireNonNull(getActivity())).setRecipeItem(topRecipe);
            topName.setText(topRecipe.getRecipeName());
            topType.setText(topRecipe.getAttributes());
        }

        return mView;
    }

    private void fillRecipes(){
        final YummlyService yummlyService = new YummlyService();
        String firstRecipe = "meat";
        YummlyService.findRecipes(firstRecipe, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response)  {
                recipes = yummlyService.processResults(response);
                topRecipe = recipes.get(0);
                recipes.remove(0);
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new ImageLoader().execute(topRecipe.getImageUrl());
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
        recipe.setImageUrl("www.google.com.br");
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("Water");
        recipe.setIngredients(ingredients);
        ArrayList<String> steps = new ArrayList<>();
        steps.add("Boil the water");
        recipe.setSteps(steps);
        recipes.add(recipe);
    }//

    @SuppressLint("StaticFieldLeak")
    public class ImageLoader extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
            topView.setImageBitmap(result);
            topName.setText(recipes.get(0).getRecipeName());
            topType.setText(recipes.get(0).getAttributes());
            ((MainActivity) Objects.requireNonNull(getContext())).progressDialog();
        }
    }
}
