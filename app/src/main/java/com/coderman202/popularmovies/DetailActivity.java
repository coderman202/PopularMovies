package com.coderman202.popularmovies;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coderman202.popularmovies.adapters.MovieDetailPagerAdapter;
import com.coderman202.popularmovies.builders.ApiUrlBuilder;
import com.coderman202.popularmovies.data.FavouritesContract;
import com.coderman202.popularmovies.interfaces.MovieDbApiInterface;
import com.coderman202.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();

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
    @BindView(R.id.fave_star) ImageView faveStarView;

    // Show either of these depending on if the user has added the movies to their favourites or not
    Drawable addStar;
    Drawable removeStar;

    Movie movie;

    // These work with the TabLayout to display the correct custom {@link Fragment}
    MovieDetailPagerAdapter pagerAdapter;
    FragmentManager fragmentManager;

    int tmdbID;

    // Columns for the favourites DB
    public static final String[] FAVE_MOVIE_ENTRY_COLUMNS = {
            FavouritesContract.FaveMovieEntry.PK_FAVE_MOVIE_KEY,
            FavouritesContract.FaveMovieEntry.TMDB_ID,
            FavouritesContract.FaveMovieEntry.POSTER_URL
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addStar = ResourcesCompat.getDrawable(getResources(), R.drawable.is_added_star, null);
        removeStar = ResourcesCompat.getDrawable(getResources(), R.drawable.is_removed_star, null);

        if(savedInstanceState == null){
            Intent intent = getIntent();
            tmdbID = intent.getIntExtra(MOVIE_ID_KEY, 0);
        }
        else{
            tmdbID = savedInstanceState.getInt(MOVIE_ID_KEY);
        }

        faveStarView.setOnClickListener(this);

        getMovieDetails();

        setupViewPager();
    }

    @Override
    public void onClick(View view){
        int id = view.getId();
        switch (id) {
            case R.id.fave_star:
                addOrRemoveFromFavourites();
                break;
        }
    }


    /**
     * Method to handle when a user adds or removes a movies from their favourites
     */
    private void addOrRemoveFromFavourites() {
        String message;
        if(faveStarView.getDrawable() == addStar){
            faveStarView.setImageDrawable(removeStar);
            message = getString(R.string.remove_from_faves, movie.getTitle());

            removeFromFaves();
        } else{
            faveStarView.setImageDrawable(addStar);
            message = getString(R.string.add_to_faves, movie.getTitle());

            addToFaves();

        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        Uri uri = FavouritesContract.FaveMovieEntry.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, FAVE_MOVIE_ENTRY_COLUMNS, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                Log.e(LOG_TAG, cursor.getString(cursor.getColumnIndex(FavouritesContract.FaveMovieEntry.POSTER_URL)));
                cursor.moveToNext();
            }
            cursor.close();
        }

    }

    // Adding an item to the favourites DD
    private void addToFaves() {
        ContentValues values = new ContentValues();
        values.put(FavouritesContract.FaveMovieEntry.TMDB_ID, tmdbID);
        values.put(FavouritesContract.FaveMovieEntry.POSTER_URL, movie.getPosterImageLink());

        Uri uri = FavouritesContract.FaveMovieEntry.CONTENT_URI;

        getContentResolver().insert(uri, values);
    }

    // Removing an item from the favourites DB
    private void removeFromFaves() {
        Uri uri = FavouritesContract.FaveMovieEntry.CONTENT_URI;

        String selection = "TMDB_ID = ?";
        String[] selectionArgs = new String[]{Integer.toString(tmdbID)};

        Cursor cursor = getContentResolver().query(uri, FAVE_MOVIE_ENTRY_COLUMNS, selection, selectionArgs, null);

        if (cursor != null) {
            cursor.moveToFirst();

            long faveID = cursor.getLong(cursor.getColumnIndex(FavouritesContract.FaveMovieEntry.PK_FAVE_MOVIE_KEY));

            uri = ContentUris.withAppendedId(FavouritesContract.FaveMovieEntry.CONTENT_URI, faveID);

            getContentResolver().delete(uri, null, null);
        }

        cursor.close();
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
            Picasso.with(this).load(backdropUrlPath).placeholder(R.drawable.backdrop_placeholder).error(R.drawable.backdrop_placeholder).into(backdropView);
        }

        if (favouritesCheck()) {
            faveStarView.setImageDrawable(addStar);
        } else {
            faveStarView.setImageDrawable(removeStar);
        }
    }

    // Method to check if the movie is in the favourites or not
    private boolean favouritesCheck() {

        Uri uri = FavouritesContract.FaveMovieEntry.CONTENT_URI;

        String selection = "TMDB_ID = ?";
        String[] selectionArgs = new String[]{Integer.toString(tmdbID)};

        Cursor cursor = getContentResolver().query(uri, FAVE_MOVIE_ENTRY_COLUMNS, selection, selectionArgs, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        return false;
    }
}
