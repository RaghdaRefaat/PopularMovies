package com.example.android.popularmovies;

import android.content.ClipData;
import android.content.Context;
import com.example.android.popularmovies.Movie;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.*;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Raghda Refaat on 31-Jul-16.
 */
public class MovieFragment extends Fragment{

    private MyCustomAdapter movieAdapter;
    GridView gridView;
    boolean Popular = false;

    public MovieFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_refresh){
            updateMovie();
        }
        else if(id == R.id.action_sort_by_most_popular) {
            Popular = true;
            Toast.makeText(getActivity(),"Please Wait Until Sorting Completing", Toast.LENGTH_LONG).show();
            updateMovie();
        }
        else if(id == R.id.action_sort_by_highest_rating) {
            Popular = false;
            Toast.makeText(getActivity(),"Please Wait Until Sorting Completing", Toast.LENGTH_LONG).show();
            updateMovie();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    private void updateMovie(){
        new FetchMovieTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Toast.makeText(getActivity(),"Please Wait Until Connecting To Internet", Toast.LENGTH_LONG).show();
         gridView = (GridView) rootView.findViewById(R.id.gridView_movie);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String MovieStr = movieAdapter.getItem(position);
                Toast.makeText(getActivity(), MovieStr , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, MovieStr);
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovie();
    }

    public class FetchMovieTask extends AsyncTask<Void,Void,ArrayList<Movie>> {
        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        @Override
        protected ArrayList<Movie> doInBackground(Void... params) {

            ArrayList<Movie> MOVIES = new ArrayList<Movie>();
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String movieJsonStr = null;
            try{
                final String BASE_URL = "http://api.themoviedb.org/3/movie/";
                final String APPID_PARAM = "api_key";
                String SORTING;
                if(Popular == true)
                    SORTING = "popular";
                else
                    SORTING = "top_rated";

                Uri builtUri = Uri.parse(BASE_URL).buildUpon().
                        appendPath(SORTING).
                        appendQueryParameter(APPID_PARAM,BuildConfig.OPEN_MOVIE_API_KEY).build();

                URL url = new URL(builtUri.toString());
                Log.v(LOG_TAG,"Built URI = "+ builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while (( line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                    line = reader.readLine();
                }
                if (buffer.length() == 0) {
                    return null;
                }
                movieJsonStr = buffer.toString();
                Log.v(LOG_TAG,"com.example.android.popularmovies.Movie JSon String "+ movieJsonStr);

            }
           catch (IOException e){
               Log.e("MovieFragment", "Error ", e);
               return null;
           }finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("MovieFragment", "Error closing stream", e);
                    }
                }
            }
            try{
                final String RESULT = "results";
                final String POSTER_PATH = "poster_path";
                final String OVERVIEW = "overview";
                final String RELEASE_DATE = "release_date";
                final String ID = "id";
                final String ORIGINAL_TITLE = "original_title";
                final String POPULARITY = "popularity";
                final String VOTE_COUNT = "vote_count";
                final String VOTE_AVG = "vote_average";

                JSONObject movieJson = new JSONObject(movieJsonStr);
                JSONArray movieArray = movieJson.getJSONArray(RESULT);

                for(int i=0 ; i<movieArray.length();i++){
                    JSONObject dataOfJson = movieArray.getJSONObject(i);
                    String posterPath = dataOfJson.getString(POSTER_PATH);
                    String overview = dataOfJson.getString(OVERVIEW);
                    String releaseDate = dataOfJson.getString(RELEASE_DATE);
                    int id = dataOfJson.getInt(ID);
                    String originalTitle = dataOfJson.getString(ORIGINAL_TITLE);
                    double popularity = dataOfJson.getDouble(POPULARITY);
                    int voteCount = dataOfJson.getInt(VOTE_COUNT);
                    double voteAvg = dataOfJson.getDouble(VOTE_AVG);
                    Movie movie = new Movie(posterPath,overview,releaseDate,originalTitle,id,popularity,voteCount,voteAvg);
                    MOVIES.add(i,movie);
                }
                return MOVIES;

            } catch (JSONException e){
                Log.e(LOG_TAG,e.getMessage(),e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> result){
            if(result != null){
                String [] res =  new String[result.size()];
                for(int i = 0 ; i < result.size();i++){
                    res[i] = result.get(i).getPosterPath();
                }
                movieAdapter = new MyCustomAdapter(getContext(),res);
                gridView.setAdapter(movieAdapter);
            }
        }

    }

}
