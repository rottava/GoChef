package com.jr.gochef;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;
import java.util.List;

public class RecycleListAdapter extends RecyclerView.Adapter<RecycleListAdapter.ViewHolder> {

    private List<Recipe> mRecipes;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;

    // data is passed into the constructor
    RecycleListAdapter(Context context, List<Recipe> mRecipes) {
        this.mInflater = LayoutInflater.from(context);
        this.mRecipes = mRecipes;
        this.mContext=context;
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
        String recipe = mRecipes.get(position).getRecipeName();
        try{
            URL url = new URL(mRecipes.get(position).getImageUrl());
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            holder.mImageView.setImageBitmap(bmp);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        holder.mTextView.setText(recipe);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        if(mRecipes==null){
            return 0;
        }else{
            return mRecipes.size();
        }
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImageView;
        TextView mTextView;

        ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.itemRecipeImage);
            mTextView = itemView.findViewById(R.id.itemRecipeText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            ((MainActivity)mContext).showButton();
            ((MainActivity)mContext).setLastItem(position);
            ((MainActivity)mContext).setRecipeItem(mRecipes.get(position));
            ((MainActivity)mContext).replaceFragment(new ExpFragment());
        }
    }

    // convenience method for getting data at click position
    public Recipe getItem(int id) {
        return mRecipes.get(id);
    }

    // allows clicks events to be caught
    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position, Recipe recycleListItem);
    }
}