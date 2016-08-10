package com.example.android.popularmovies;

import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;

import com.squareup.picasso.Picasso;

/**
 * Created by Raghda Refaat on 31-Jul-16.
 */
public class MovieFragment extends Fragment{
    private ArrayAdapter<String> movieAdapter;


    public MovieFragment(){
        Picasso.with(this.getContext()).load("http://i.imgur.com/DvpvklR.png").into(R.id.image_movie);
    }

}
