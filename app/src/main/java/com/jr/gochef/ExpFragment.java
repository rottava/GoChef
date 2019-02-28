package com.jr.gochef;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ExpFragment extends Fragment {

    private TextView expType;
    private TextView expName;
    private TextView expIngredients;
    private TextView expSteps;
    private ImageView expBigImage;
    private ImageView expBack;
    private ImageView expShare;
    private ImageView expFav;
    private ScrollView mScrollView;
    private RecycleListAdapter adapter;
    RecyclerView mRecyclerView;
    private ArrayList<Recipe> recipes;
    private Recipe mRecipe;
    private int fav;

    public ExpFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Please note the third parameter should be false, otherwise a java.lang.IllegalStateException maybe thrown.
        View mView = inflater.inflate(R.layout.fragment_exp, container, false);
        mRecyclerView = mView.findViewById(R.id.expListMore);
        expType = mView.findViewById(R.id.expRecipeType);
        expName = mView.findViewById(R.id.expRecipeName);
        expIngredients = mView.findViewById(R.id.expIngredientsList);
        expSteps = mView.findViewById(R.id.expStepsList);
        expBigImage = mView.findViewById(R.id.expImageView);
        expBack = mView.findViewById(R.id.expBack);
        expBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((MainActivity)getActivity()).reloadFragment();
            }
        });
        expShare = mView.findViewById(R.id.expShare);
        expShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("https://developers.facebook.com"))
                        .build();
                ShareDialog.show(getActivity(), content);
                ///TODO
            }
        });
        expFav = mView.findViewById(R.id.expFav);
        fav = 0;
        expFav.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(fav == 0){
                    expFav.setBackgroundResource(R.drawable.ic_fav);
                    fav = 1;
                } else {
                    expFav.setBackgroundResource(R.drawable.ic_fav_out);
                    fav = 0;
                }
            }
            ///TODO
        });

        mRecipe = ((MainActivity)getActivity()).getRecipeItem();
        expName.setText(mRecipe.getRecipeName());
        expType.setText(mRecipe.getAttributes());

        try{
            URL url = new URL(mRecipe.getImageUrl());
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            expBigImage.setImageBitmap(bmp);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        String text="\n";
        for(String elemento: mRecipe.getIngredients()){
            text = text + "• " + elemento + "\n";
        }
        expIngredients.setText(text);

        text="\n";
        int n=1;
        for(String elemento: mRecipe.getIngredients()
        ){
            text = text + n +") " + elemento + "\n";
            n++;
        }
        expSteps.setText(text);

        fillRecipes();
        if(recipes == null || recipes.size() == 0){
            fillRecipesTest();
        }

        return mView;

    }

    private void fillRecipes(){
        final YummlyService yummlyService = new YummlyService();
        yummlyService.findRecipes(mRecipe.getSource(), new Callback() {
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