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
import com.coderman202.popularmovies.adapters.ReviewListAdapter;
import com.coderman202.popularmovies.builders.ApiUrlBuilder;
import com.coderman202.popularmovies.design.DividerItemDecoration;
import com.coderman202.popularmovies.interfaces.MovieDbApiInterface;
import com.coderman202.popularmovies.model.MovieReview;
import com.coderman202.popularmovies.responses.MovieReviewJsonResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *Created by Reggie on 25/03/2018.
 *Custom fragment to display the trailers of the movie
 */

public class ReviewsFragment extends Fragment {

    public static final String LOG_TAG = ReviewsFragment.class.getSimpleName();

    // Key to pass and retrieve the Tmdb ID of the movie.
    public static final String MOVIE_ID_KEY = "ID";

    // All views to be populated in the layout
    @BindView(R.id.review_count) TextView reviewCountView;
    @BindView(R.id.reviews_list_view) RecyclerView reviewListView;

    private int tmdbID;
    List<MovieReview> movieReviewList = new ArrayList<>();

    ReviewListAdapter reviewListAdapter;

    LinearLayoutManager reviewListLayoutManager;

    DividerItemDecoration dividerItemDecoration;

    public ReviewsFragment() {
    }

    public void setMovieIdKey(int tmdbID) {
        this.tmdbID = tmdbID;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.reviews_layout, container, false);

        ButterKnife.bind(this, rootView);

        if(savedInstanceState != null){
            tmdbID = savedInstanceState.getInt(MOVIE_ID_KEY);
        }

        initRecyclerView();

        getMovieReviews();

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle saveState) {
        super.onSaveInstanceState(saveState);
        saveState.putInt(MOVIE_ID_KEY, tmdbID);
    }

    /**
     * Get the movie trailersusing the defined interface in:
     * {@link MovieDbApiInterface}
     */
    private void getMovieReviews(){

        try{
            MovieDbApiInterface movieDbApiInterface =
                    ApiUrlBuilder.getRetrofitClient(this.getContext()).create(MovieDbApiInterface.class);
            final Call<MovieReviewJsonResponse> movieJsonResponseCall =
                    movieDbApiInterface.getMovieReviewList(tmdbID, ApiUrlBuilder.API_KEY);

            movieJsonResponseCall.enqueue(new Callback<MovieReviewJsonResponse>() {
                @Override
                public void onResponse(Call<MovieReviewJsonResponse> call, Response<MovieReviewJsonResponse> response) {
                    if(response.isSuccessful()){
                        movieReviewList = response.body().getReviewList();
                        reviewListAdapter.setReviewList(movieReviewList);
                    }
                }

                @Override
                public void onFailure(Call<MovieReviewJsonResponse> call, Throwable t) {
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
        reviewListLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        reviewListView.setLayoutManager(reviewListLayoutManager);
        reviewListView.setHasFixedSize(true);
        ColorDrawable divider = new ColorDrawable(ContextCompat.getColor(getContext(), R.color.greyed_text_color));
        reviewListView.addItemDecoration(new DividerItemDecoration(divider));
        reviewListAdapter = new ReviewListAdapter();
        reviewListView.setAdapter(reviewListAdapter);

        Log.e(LOG_TAG, "review count:" + reviewListAdapter.getItemCount());

        if(reviewListAdapter.getItemCount() == 0){
            reviewListView.setVisibility(View.GONE);
            reviewCountView.setVisibility(View.VISIBLE);
        }
        else{
            reviewListView.setVisibility(View.VISIBLE);
            reviewCountView.setVisibility(View.GONE);
        }

    }
}
