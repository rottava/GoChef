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
    private ImageView expBigImage;
    private ImageView expBack;
    private ImageView expShare;
    private ImageView expFav;
    private ScrollView mScrollView;
    private RecycleListAdapter adapter;

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
        expBigImage = mView.findViewById(R.id.expImageView);
        expBack = mView.findViewById(R.id.expBack);
        expBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int index = getActivity().getFragmentManager().getBackStackEntryCount() - 1;
                FragmentManager.BackStackEntry backEntry = getFragmentManager().getBackStackEntryAt(index);
                String tag = backEntry.getName();
                Fragment fragment = getFragmentManager().findFragmentByTag(tag);
                ((MainActivity)getActivity()).replaceFragment(fragment);
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
        expFav.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                expFav.setBackgroundResource(R.drawable.ic_fav);
            }
            ///TODO
        });

        mScrollView =  mView.findViewById(R.id.expScrollView);
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
        RecyclerView recyclerView = mView.findViewById(R.id.expListIngredients);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new RecycleListAdapter(getContext(), favNames);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        // set up the RecyclerView
        recyclerView = mView.findViewById(R.id.expListSteps);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new RecycleListAdapter(getContext(), favNames);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        // set up the RecyclerView
        recyclerView = mView.findViewById(R.id.expListMore);
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