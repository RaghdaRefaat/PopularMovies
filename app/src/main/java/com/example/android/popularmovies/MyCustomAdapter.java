package com.example.android.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Raghda Refaat on 13-Aug-16.
 */
public class MyCustomAdapter extends BaseAdapter {

    ArrayList<Movie> mMovies;
    Context mContext;

    public MyCustomAdapter(Context context, ArrayList<Movie> movies){
        mContext = context;
        mMovies = movies;
    }

    @Override
    public int getCount() {
        return mMovies.size();
    }

    @Override
    public Movie getItem(int position) {
        return mMovies.get(position);
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
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/"+ mMovies.get(position).getPosterPath()).into(img);
        return  convertView;
    }
}
