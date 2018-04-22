package com.coderman202.popularmovies.fragments;

import android.content.Intent;
import android.net.Uri;
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
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Reggie on 25/03/2018.
 * Custom fragment to display the details of the movie
 */

public class DetailsFragment extends Fragment {

    public static final String LOG_TAG = DetailsFragment.class.getSimpleName();

    // Key to pass and retrieve the Tmdb ID of the movie.
    public static final String MOVIE_ID_KEY = "ID";

    // All views to be populated in the layout
    @BindView(R.id.movie_language) TextView languageView;
    @BindView(R.id.movie_release_date) TextView releaseDateView;
    @BindView(R.id.movie_budget) TextView budgetView;
    @BindView(R.id.movie_revenue) TextView revenueView;
    @BindView(R.id.homepage_link) TextView homepageView;
    @BindView(R.id.imdb_link) TextView imdbView;
    @BindView(R.id.tmdb_link) TextView tmdbView;

    private int tmdbID;
    private Movie movie;

    public DetailsFragment() {
    }

    public void setMovieIdKey(int tmdbID) {
        this.tmdbID = tmdbID;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.details_layout, container, false);

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
        releaseDateView.setText(movie.getReleaseDate(Locale.getDefault()));

        languageView.setText(movie.getMainLanguage());

        budgetView.setText(movie.getBudgetToString());

        revenueView.setText(movie.getRevenueToString());

        imdbView.setText(getResources().getString(R.string.imdb_link, movie.getTitle()));

        tmdbView.setText(getResources().getString(R.string.tmdb_link, movie.getTitle()));

        // Three OnClickListeners below so the user can get more info if they wish from the
        // homepage, IMDB or TMDB
        homepageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(movie.getHomepageLink()));
                startActivity(intent);
            }
        });

        imdbView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(movie.getImdbLink()));
                startActivity(intent);
            }
        });

        tmdbView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(movie.getTmdbLink()));
                startActivity(intent);
            }
        });
    }

}
