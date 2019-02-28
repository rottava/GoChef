package com.jr.gochef;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecycleListAdapter extends RecyclerView.Adapter<RecycleListAdapter.ViewHolder> {

    private List<Integer> mViewColors;
    private List<String> mRecipes;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    RecycleListAdapter(Context context, List<Integer> colors, List<String> recipes) {
        this.mInflater = LayoutInflater.from(context);
        this.mViewColors = colors;
        this.mRecipes = recipes;
    }
    // data is passed into the constructor
    RecycleListAdapter(Context context, List<String> recipes) {
        this.mInflater = LayoutInflater.from(context);
        this.mRecipes = recipes;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recipe, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int color = mViewColors.get(position);
        String recipe = mRecipes.get(position);
        holder.myView.setBackgroundColor(color);
        holder.myTextView.setText(recipe);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View myView;
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myView = itemView.findViewById(R.id.itemRecipeImage);
            myTextView = itemView.findViewById(R.id.itemRecipeText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mRecipes.get(id);
    }

    // allows clicks events to be caught
    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}