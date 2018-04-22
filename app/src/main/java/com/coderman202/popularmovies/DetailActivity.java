package com.coderman202.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.coderman202.popularmovies.adapters.MovieDetailPagerAdapter;
import com.coderman202.popularmovies.builders.ApiUrlBuilder;
import com.coderman202.popularmovies.interfaces.MovieDbApiInterface;
import com.coderman202.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    public static final String LOG_TAG = DetailActivity.class.getSimpleName();

    // Key to pass and retrieve the Tmdb ID of the movie.
    public static final String MOVIE_ID_KEY = "ID";

    // All views to be populated in the layout
    @BindView(R.id.movie_title) TextView titleView;
    @BindView(R.id.movie_backdrop_img) ImageView backdropView;
    @BindView(R.id.movie_genres) TextView genresView;
    @BindView(R.id.movie_runtime) TextView runtimeView;
    @BindView(R.id.movie_rating) TextView ratingView;
    @BindView(R.id.movie_vote_count) TextView voteCountView;
    @BindView(R.id.movie_details_tablayout) TabLayout detailsTab;
    @BindView(R.id.movie_details_viewpager) ViewPager detailsViewPager;

    Movie movie;

    MovieDetailPagerAdapter pagerAdapter;
    FragmentManager fragmentManager;

    int tmdbID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState == null){
            Intent intent = getIntent();
            tmdbID = intent.getIntExtra(MOVIE_ID_KEY, 0);
        }
        else{
            tmdbID = savedInstanceState.getInt(MOVIE_ID_KEY);
        }

        getMovieDetails();

        setupViewPager();
    }

    /**
     *
     */
    private void setupViewPager() {
        fragmentManager = getSupportFragmentManager();
        pagerAdapter = new MovieDetailPagerAdapter(fragmentManager, this, tmdbID);
        detailsViewPager.setAdapter(pagerAdapter);
        detailsTab.setupWithViewPager(detailsViewPager);
    }

    // Handling screen rotation
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(MOVIE_ID_KEY, tmdbID);
    }

    // Handling screen rotation
    @Override
    public void onRestoreInstanceState(Bundle inState){
        tmdbID = inState.getInt(MOVIE_ID_KEY);
    }

    // Providing up navigation.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Get the movie details using the defined interface in:
     * {@link MovieDbApiInterface}
     */
    private void getMovieDetails(){

        try{
            MovieDbApiInterface movieDbApiInterface =
                    ApiUrlBuilder.getRetrofitClient(this).create(MovieDbApiInterface.class);
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
        titleView.setText(movie.getTitleDetailed());

        genresView.setText(movie.getGenresToString());

        runtimeView.setText(movie.getRuntimeToString());

        ratingView.setText(movie.getUserRatingToString());

        voteCountView.setText(getResources().getString(R.string.vote_count, movie.getVoteCountToString()));

        String backdropUrlPath = movie.getBackdropImageLink();

        if(!TextUtils.isEmpty(backdropUrlPath)){
            backdropUrlPath = ApiUrlBuilder.BACKDROP_POSTER_PATH_BASE_URL + backdropUrlPath;
            Picasso.with(this).load(backdropUrlPath).into(backdropView);
        }
    }
}
