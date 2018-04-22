package com.coderman202.popularmovies.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coderman202.popularmovies.R;
import com.coderman202.popularmovies.builders.ApiUrlBuilder;
import com.coderman202.popularmovies.interfaces.MovieDbApiInterface;
import com.coderman202.popularmovies.model.Movie;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Reggie on 25/03/2018.
 * Custom fragment to display the overview of the movie
 */

public class OverviewFragment extends Fragment {

    public static final String LOG_TAG = OverviewFragment.class.getSimpleName();

    // Key to pass and retrieve the Tmdb ID of the movie.
    public static final String MOVIE_ID_KEY = "ID";

    // All views to be populated in the layout
    @BindView(R.id.movie_overview) TextView overviewView;

    private int tmdbID;
    private Movie movie;

    public OverviewFragment() {
    }

    public void setMovieIdKey(int tmdbID) {
        this.tmdbID = tmdbID;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.overview_layout, container, false);

        ButterKnife.bind(this, rootView);

        if(savedInstanceState != null){
            tmdbID = savedInstanceState.getInt(MOVIE_ID_KEY);
        }

        getMovieDetails();

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle saveState) {
        super.onSaveInstanceState(saveState);
        saveState.putInt(MOVIE_ID_KEY, tmdbID);
    }

    /**
     * Get the movie details using the defined interface in:
     * {@link MovieDbApiInterface}
     */
    private void getMovieDetails(){

        try{
            MovieDbApiInterface movieDbApiInterface =
                    ApiUrlBuilder.getRetrofitClient(this.getContext()).create(MovieDbApiInterface.class);
            final Call<Movie> movieJsonResponseCall =
                    movieDbApiInterface.getMovieDetails(tmdbID, ApiUrlBuilder.API_KEY);

            movieJsonResponseCall.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    if(response.isSuccessful()){
                        movie = response.body();
                        populateUI();
                    }
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                }
            });

        } catch (IOException e){
            Log.e(LOG_TAG, e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Populate all the views in the UI with the movie data.
     */
    private void populateUI() {
        overviewView.setText(movie.getOverview());
    }
}
