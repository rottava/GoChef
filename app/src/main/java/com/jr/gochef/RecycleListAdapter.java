package com.jr.gochef;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecycleListAdapter extends RecyclerView.Adapter<RecycleListAdapter.ViewHolder> {

    private List<Recipe> mRecipes;
    private LayoutInflater mInflater;
    private Context mContext;
    private ArrayList<ViewHolder> mViewHolder = new ArrayList<>();

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
        mViewHolder.add(holder);
        new ImageLoader().execute(Integer.toString(position));
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
            ((MainActivity)mContext).progressDialog();
            ((MainActivity)mContext).showButton();
            ((MainActivity)mContext).setRecipeItem(mRecipes.get(position));
            ((MainActivity)mContext).replaceFragment(new ExpFragment());
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class ImageLoader extends AsyncTask<String, Void, Bitmap> {
        private int position;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap bitmap;
            position = Integer.parseInt(urls[0]);
            try {
                bitmap = Helper.downloadDataFromUrl(mRecipes.get(position).imageUrl);
            } catch (IOException e) {
                e.printStackTrace();
                bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_404);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute( Bitmap result )  {
            mViewHolder.get(position).mTextView.setText(mRecipes.get(position).getRecipeName());
            mViewHolder.get(position).mImageView.setImageBitmap(result);
            if(mViewHolder.get(position).mImageView.getDrawable() == null) {
                mViewHolder.get(position).mImageView.setImageResource(R.drawable.ic_404);
            }
        }
    }

}