package com.jr.gochef;

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

import java.util.ArrayList;

public class ExpFragment extends Fragment implements RecycleListAdapter.ItemClickListener {

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
    private RecycleListItem recycleListItem;
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

        recycleListItem = ((MainActivity)getActivity()).getRecycleListItem();
        expName.setText(recycleListItem.getName());
        expBigImage.setBackgroundColor(recycleListItem.getImage());
        String text="\n";
        for(String elemento: recycleListItem.getIngredients()){
            text = text + "• " + elemento + "\n";
        }
        expIngredients.setText(text);
        text="\n";
        int n=1;
        for(String elemento: recycleListItem.getSteps()){
            text = text + n +") " + elemento + "\n";
            n++;
        }
        expSteps.setText(text);

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
        RecyclerView recyclerView = mView.findViewById(R.id.expListMore);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        adapter = new RecycleListAdapter(getContext(), recipes);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        return mView;
    }

    @Override
    public void onItemClick(View view, int position, RecycleListItem recycleListItem) {
        ((MainActivity)getActivity()).setLastItem(position);
        ((MainActivity)getActivity()).setRecycleListItem(recycleListItem);
        ((MainActivity)getActivity()).replaceFragment(new ExpFragment());
    }

}