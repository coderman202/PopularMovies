package com.coderman202.popularmovies.fragments;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coderman202.popularmovies.R;
import com.coderman202.popularmovies.adapters.TrailerListAdapter;
import com.coderman202.popularmovies.builders.ApiUrlBuilder;
import com.coderman202.popularmovies.design.DividerItemDecoration;
import com.coderman202.popularmovies.interfaces.MovieDbApiInterface;
import com.coderman202.popularmovies.model.MovieTrailer;
import com.coderman202.popularmovies.responses.MovieTrailerJsonResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Reggie on 25/03/2018.
 * Custom fragment to display the trailers of the movie
 */

public class TrailersFragment extends Fragment {

    private static final String LOG_TAG = TrailersFragment.class.getSimpleName();

    // Key to pass and retrieve the Tmdb ID of the movie.
    public static final String MOVIE_ID_KEY = "ID";

    // All views to be populated in the layout
    @BindView(R.id.trailers_list_view) RecyclerView trailersListView;
    @BindView(R.id.trailer_count) TextView trailerCountView;

    TrailerListAdapter trailerListAdapter;

    LinearLayoutManager trailerListLayoutManager;

    private int tmdbID;
    List<MovieTrailer> movieTrailerList = new ArrayList<>();

    public TrailersFragment() {
    }

    public void setMovieIdKey(int tmdbID) {
        this.tmdbID = tmdbID;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.trailers_layout, container, false);

        ButterKnife.bind(this, rootView);

        if(savedInstanceState != null){
            tmdbID = savedInstanceState.getInt(MOVIE_ID_KEY);
        }

        initRecyclerView();

        getMovieTrailers();

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle saveState) {
        super.onSaveInstanceState(saveState);
        saveState.putInt(MOVIE_ID_KEY, tmdbID);
    }

    /**
     * Get the movie trailers using the defined interface in:
     * {@link MovieDbApiInterface}
     */
    private void getMovieTrailers(){

        try{
            MovieDbApiInterface movieDbApiInterface =
                    ApiUrlBuilder.getRetrofitClient(this.getContext()).create(MovieDbApiInterface.class);
            final Call<MovieTrailerJsonResponse> movieJsonResponseCall =
                    movieDbApiInterface.getMovieTrailerList(tmdbID, ApiUrlBuilder.API_KEY);

            movieJsonResponseCall.enqueue(new Callback<MovieTrailerJsonResponse>() {
                @Override
                public void onResponse(Call<MovieTrailerJsonResponse> call, Response<MovieTrailerJsonResponse> response) {
                    if(response.isSuccessful()){
                        movieTrailerList = response.body().getTrailerList();
                        trailerListAdapter.setTrailerList(movieTrailerList);
                        setEmptyView();
                    }
                }

                @Override
                public void onFailure(Call<MovieTrailerJsonResponse> call, Throwable t) {
                }
            });

        } catch (IOException e){
            Log.e(LOG_TAG, e.toString());
            e.printStackTrace();
        }

    }

    /**
     * Method to initialise the recycler view and set both layout manager and adapter
     */
    private void initRecyclerView() {
        trailerListLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        trailersListView.setLayoutManager(trailerListLayoutManager);
        trailersListView.setHasFixedSize(true);
        ColorDrawable divider = new ColorDrawable(ContextCompat.getColor(getContext(), R.color.greyed_text_color));
        trailersListView.addItemDecoration(new DividerItemDecoration(divider));
        trailerListAdapter = new TrailerListAdapter(this.getContext());
        trailersListView.setAdapter(trailerListAdapter);
    }

    //If there are no trailers, show TextView telling user that.
    private void setEmptyView() {
        if(trailerListAdapter.getItemCount() == 0){
            trailersListView.setVisibility(View.GONE);
            trailerCountView.setVisibility(View.VISIBLE);
        } else{
            trailersListView.setVisibility(View.VISIBLE);
            trailerCountView.setVisibility(View.GONE);
        }
    }
}
