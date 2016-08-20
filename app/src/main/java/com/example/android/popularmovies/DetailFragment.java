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
    String mWeatherStr;

    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            mWeatherStr = intent.getStringExtra(Intent.EXTRA_TEXT);
            //(TextView) rootView.findViewById(R.id.detail_text).setText(mForecastStr);
        }
        return rootView;
    }
}
