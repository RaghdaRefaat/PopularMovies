package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Raghda Refaat on 20-Aug-16.
 */
public class DetailFragment extends Fragment {
    Movie movie;

    public DetailFragment() {
        //setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            movie = (Movie) intent.getSerializableExtra(Intent.EXTRA_TEXT);
            TextView textView = (TextView) rootView.findViewById(R.id.detail_text);
            textView.setText(movie.getOriginalTitle());
        }
        return rootView;
    }
}
