package com.coderman202.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.coderman202.popularmovies.builders.ApiUrlBuilder;
import com.coderman202.popularmovies.interfaces.MovieDbApiInterface;
import com.coderman202.popularmovies.model.Movie;
import com.coderman202.popularmovies.views.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    public static final String LOG_TAG = DetailsActivity.class.getSimpleName();

    @BindView(R.id.movie_title) TextView titleView;
    @BindView(R.id.movie_overview)
    ExpandableTextView overviewView;
    @BindView(R.id.movie_backdrop_img)
    ImageView backdropView;
    @BindView(R.id.movie_genres) TextView genresView;
    @BindView(R.id.movie_release_date) TextView releaseDateView;
    @BindView(R.id.movie_runtime) TextView runtimeView;
    @BindView(R.id.movie_rating) TextView ratingView;
    @BindView(R.id.movie_vote_count) TextView voteCountView;
    @BindView(R.id.movie_budget) TextView budgetView;
    @BindView(R.id.movie_revenue) TextView revenueView;

    static Movie movie;

    static int tmdbID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        Intent intent = getIntent();

        tmdbID = intent.getIntExtra("ID", 0);

        getMovieDetails();

        populateUI();
    }

    private void getMovieDetails(){

        try{
            Log.e(LOG_TAG, "test2");
            MovieDbApiInterface movieDbApiInterface =
                    ApiUrlBuilder.getRetrofitClient(this).create(MovieDbApiInterface.class);
            final Call<Movie> movieJsonResponseCall =
                    movieDbApiInterface.getMovieDetails(tmdbID, ApiUrlBuilder.API_KEY);

            Log.e(LOG_TAG, "test3");
            movieJsonResponseCall.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    int responseCode = response.code();
                    Log.e(LOG_TAG, responseCode + "");
                    if(response.isSuccessful()){
                        movie = response.body();
                        Log.e(LOG_TAG + "1", movie.toString());
                    }
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                    Log.e(LOG_TAG + "1", "Why? :(");
                }
            });

        } catch (IOException e){
            Log.e(LOG_TAG, e.toString());
            e.printStackTrace();
        }
    }

    private void populateUI() {
        titleView.setText(movie.getTitleDetailed());
        String backdropUrlPath = movie.getBackdropImageLink();

        if(!TextUtils.isEmpty(backdropUrlPath)){
            backdropUrlPath = ApiUrlBuilder.BACKDROP_POSTER_PATH_BASE_URL + backdropUrlPath;
            Picasso.with(this).load(backdropUrlPath).into(backdropView);
        }
        overviewView.setText(movie.getOverview());

        genresView.setText(movie.getGenresToString());
        Log.e(LOG_TAG, movie.getGenresToString());
        Log.e(LOG_TAG, movie.getGenres().toString());

        releaseDateView.setText(movie.getReleaseDate());

        runtimeView.setText(movie.getRuntimeToString());

        ratingView.setText(movie.getUserRatingToString());

        voteCountView.setText(movie.getVoteCountToString());

        budgetView.setText(movie.getBudgetToString());
        Log.e(LOG_TAG, Integer.toString(movie.getBudget()));

        revenueView.setText(movie.getRevenueToString());
        Log.e(LOG_TAG, Integer.toString(movie.getRevenue()));

        Log.e(LOG_TAG, movie.toString());


    }
}
