package com.jr.gochef;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ExpFragment extends Fragment {

    private ImageView expFav;
    private ImageView expBigImage;
    private RecycleListAdapter adapter;
    private RecyclerView mRecyclerView;
    private ArrayList<Recipe> recipes;
    private Recipe mRecipe;
    private int fav;
    private User user;

    public ExpFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Please note the third parameter should be false, otherwise a java.lang.IllegalStateException maybe thrown.
        View mView = inflater.inflate(R.layout.fragment_exp, container, false);
        user = ((MainActivity) Objects.requireNonNull(getActivity())).getUser();
        mRecipe = ((MainActivity)getActivity()).getRecipeItem();
        mRecyclerView = mView.findViewById(R.id.expListMore);
        TextView expType = mView.findViewById(R.id.expRecipeType);
        TextView expName = mView.findViewById(R.id.expRecipeName);
        TextView expIngredients = mView.findViewById(R.id.expIngredientsList);
        TextView expSteps = mView.findViewById(R.id.expStepsList);
        expBigImage = mView.findViewById(R.id.expImageView);
        ImageView expBack = mView.findViewById(R.id.expBack);
        expBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((MainActivity) Objects.requireNonNull(getActivity())).progressDialog();
                ((MainActivity) Objects.requireNonNull(getActivity())).setUser(user);
                ((MainActivity)getActivity()).reloadFragment();
            }
        });
        ImageView expShare = mView.findViewById(R.id.expShare);
        expShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("https://developers.facebook.com"))
                        .build();
                ShareDialog.show(Objects.requireNonNull(getActivity()), content);
            }
        });
        expFav = mView.findViewById(R.id.expFav);
        int loop;
        for(loop=0; loop < user.getFavorites().size(); loop++){
            if(user.getFavorites().get(loop).getRecipeName().equals(mRecipe.getRecipeName())){
                fav = 1;
                expFav.setBackgroundResource(R.drawable.ic_fav);
                loop = user.getFavorites().size();
            } else {
                if(loop == user.getFavorites().size()-1){
                    fav = 0;
                    expFav.setBackgroundResource(R.drawable.ic_fav_out);
                }
            }
        }
        expFav.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int loop;
                if(fav == 0){
                    fav = 1;
                    expFav.setBackgroundResource(R.drawable.ic_fav);
                    user.getFavorites().add(mRecipe);
                } else {
                    for(loop=0; loop < user.getFavorites().size(); loop++) {
                        if (user.getFavorites().get(loop).getRecipeName().equals(mRecipe.getRecipeName())) {
                            fav = 0;
                            expFav.setBackgroundResource(R.drawable.ic_fav_out);
                            user.getFavorites().remove(loop);
                            loop = user.getFavorites().size();
                        }
                    }
                }
            }
        });

        expName.setText(mRecipe.getRecipeName());
        expType.setText(mRecipe.getAttributes());

        new ImageLoader().execute(mRecipe.getImageUrl());

        StringBuilder text= new StringBuilder("\n");
        for(String elemento: mRecipe.getIngredients()){
            text.append("• ").append(elemento).append("\n");
        }
        expIngredients.setText(text.toString());
        text = new StringBuilder("\n");
        int n=1;
        for(String elemento: mRecipe.getSteps()
        ){
            text.append(n).append(") ").append(elemento).append("\n");
            n++;
        }
        expSteps.setText(text.toString());

        fillRecipes();

        return mView;
    }

    private void fillRecipes(){
        final YummlyService yummlyService = new YummlyService();
        YummlyService.findRecipes(mRecipe.getIngredients().get(0), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response)  {
                recipes = yummlyService.processResults(response);
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
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
            expBigImage.setImageBitmap(result);
            ((MainActivity) Objects.requireNonNull(getContext())).progressDialog();
        }
    }

}