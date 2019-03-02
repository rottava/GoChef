package com.jr.gochef;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

public class FavFragment extends Fragment {

    public FavFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_fav, container, false);
        User user = ((MainActivity) Objects.requireNonNull(getContext())).getUser();
        List<Recipe> recipes = user.getFavorites();
        if(user.getFavorites().size() == 0){
            ((MainActivity) Objects.requireNonNull(getContext())).progressDialog();
        }
        RecyclerView mRecyclerView = mView.findViewById(R.id.listaReceita);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        RecycleListAdapter adapter = new RecycleListAdapter(getContext(), recipes);
        mRecyclerView.setAdapter(adapter);

        return mView;
    }
}