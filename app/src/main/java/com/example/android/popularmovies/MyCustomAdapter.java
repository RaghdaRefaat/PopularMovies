package com.example.android.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Raghda Refaat on 13-Aug-16.
 */
public class MyCustomAdapter extends BaseAdapter {

    String[] mMoviesImages;
    Context mContext;
    public MyCustomAdapter(Context context,String[] moviesImages){
        mContext = context;
        mMoviesImages = moviesImages;
    }

    @Override
    public int getCount() {
        return mMoviesImages.length;
    }

    @Override
    public String getItem(int position) {
        return mMoviesImages[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_movie, null);
        }
        ImageView img = (ImageView) convertView.findViewById(R.id.image_movie);
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/"+mMoviesImages[position]).into(img);
        return  convertView;
    }
}
