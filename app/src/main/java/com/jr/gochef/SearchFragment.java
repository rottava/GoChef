package com.jr.gochef;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SearchFragment extends Fragment {

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Please note the third parameter should be false, otherwise a java.lang.IllegalStateException maybe thrown.
        View mView = inflater.inflate(R.layout.fragment_search, container, false);
        search = Objects.requireNonNull(getArguments()).getString("search");
        mRecyclerView = mView.findViewById(R.id.searchList);
        TextView searchType = mView.findViewById(R.id.searchText);
        searchType.setText(search);
        ImageView searchBack = mView.findViewById(R.id.searchView);
        searchBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((MainActivity) Objects.requireNonNull(getActivity())).progressDialog();
                FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
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
        YummlyService.findRecipes(search, new Callback() {
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

}