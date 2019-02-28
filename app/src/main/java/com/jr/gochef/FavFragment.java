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
import android.widget.Toast;

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
        mScrollView =  mView.findViewById(R.id.favScrollView);
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

        ArrayList<String> favNames = new ArrayList<>();
        favNames.add("Boiled Water");
        favNames.add("Awesome Barbecue");
        favNames.add("Dry Camel and gin");
        favNames.add("German Black Sheep n®12");
        favNames.add("Roasted Goat w/ Honey n' pepper");

        // set up the RecyclerView
        RecyclerView recyclerView = mView.findViewById(R.id.listaReceita);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new RecycleListAdapter(getContext(), viewColors, favNames);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        return mView;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getContext(), "You clicked " + adapter.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
    }

}