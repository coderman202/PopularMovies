package com.coderman202.popularmovies;

import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.coderman202.popularmovies.adapters.MovieListAdapter;
import com.coderman202.popularmovies.builders.ApiUrlBuilder;
import com.coderman202.popularmovies.data.FavouritesContract;
import com.coderman202.popularmovies.interfaces.MovieDbApiInterface;
import com.coderman202.popularmovies.model.Movie;
import com.coderman202.popularmovies.responses.MovieJsonResponse;
import com.coderman202.popularmovies.utilities.NetworkConnectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    public static final String USER_PREF_KEY = "user_pref";

    public static final int PORTRAIT_SPAN_COUNT = 4;
    public static final int LANDSCAPE_SPAN_COUNT = 6;

    // Setting the number of pages of results. Future versions may allow user to specify the number.
    public static final int NUM_RESULTS_PAGES = 5;

    // All the UI elements
    @BindView(R.id.movie_list)
    RecyclerView movieListView;
    @BindView(R.id.no_connection)
    TextView noConnectionView;
    @BindView(R.id.no_favourites)
    TextView noFavouritesView;
    @BindView(R.id.no_results)
    TextView noResultsView;

    // Movie List Adapter
    MovieListAdapter movieListAdapter;

    // Layout Manager for Movie List Adapter
    GridLayoutManager movieListLayoutManager;

    // Key for saving the scroll position of the RecyclerView.
    public static final String BUNDLE_RECYCLER_LAYOUT_KEY = "RecyclerView Layout";

    // Number grid items to span the screen. To be set depending on the screen rotation
    int layoutSpanCount;

    // Setting the default selection for which movie list to display
    String userPref = ApiUrlBuilder.OPTION_NOW_PLAYING;
    @BindString(R.string.now_playing) String actionBarPrefTitle;

    ActionBar actionBar;
    @BindString(R.string.app_name) String actionBarMainTitle;

    // To represent the full title for the Action Bar
    String actionBarFullTitle;

    // Columns for the favourites DB
    public static final String[] FAVE_MOVIE_ENTRY_COLUMNS = {
            FavouritesContract.FaveMovieEntry.PK_FAVE_MOVIE_KEY,
            FavouritesContract.FaveMovieEntry.TMDB_ID,
            FavouritesContract.FaveMovieEntry.POSTER_URL
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        actionBar = this.getSupportActionBar();

        if(savedInstanceState != null){
            userPref = savedInstanceState.getString(USER_PREF_KEY);
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT_KEY);
            movieListLayoutManager.onRestoreInstanceState(savedRecyclerLayoutState);
            Log.e(LOG_TAG, userPref);
        } else {
            initRecyclerView();

            loadMovieList();

            //checkForResults();
        }
        setActionBarTitle();
    }

    // Handling screen rotations.
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString(USER_PREF_KEY, userPref);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT_KEY, movieListLayoutManager.onSaveInstanceState());
    }

    // Handling screen rotations.
    @Override
    public void onRestoreInstanceState(Bundle inState){
        userPref = inState.getString(USER_PREF_KEY);
        Parcelable savedRecyclerLayoutState = inState.getParcelable(BUNDLE_RECYCLER_LAYOUT_KEY);
        movieListLayoutManager.onRestoreInstanceState(savedRecyclerLayoutState);
    }

    /**
     * Set the title depending on the list of movies as selected in the options menu by the user.
     */
    private void setActionBarTitle() {
        switch (userPref) {
            case ApiUrlBuilder.OPTION_UPCOMING:
                actionBarPrefTitle = getString(R.string.upcoming);
                break;
            case ApiUrlBuilder.OPTION_POPULAR:
                actionBarPrefTitle = getString(R.string.popular);
                break;
            case ApiUrlBuilder.OPTION_NOW_PLAYING:
                actionBarPrefTitle = getString(R.string.now_playing);
                break;
            case ApiUrlBuilder.OPTION_TOP_RATED:
                actionBarPrefTitle = getString(R.string.top_rated);
                break;
            case ApiUrlBuilder.OPTION_FAVOURITES:
                actionBarPrefTitle = getString(R.string.favourites);
                break;
        }
        actionBarFullTitle = actionBarMainTitle + " - " + actionBarPrefTitle;
        actionBar.setTitle(actionBarFullTitle);
    }

    // Create the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);

        return true;
    }

    // Reload the movie list depending on the option chosen
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.upcoming_option:
                userPref = ApiUrlBuilder.OPTION_UPCOMING;
                break;
            case R.id.pop_option:
                userPref = ApiUrlBuilder.OPTION_POPULAR;
                break;
            case R.id.now_playing_option:
                userPref = ApiUrlBuilder.OPTION_NOW_PLAYING;
                break;
            case R.id.top_rated_option:
                userPref = ApiUrlBuilder.OPTION_TOP_RATED;
                break;
            case R.id.fave_option:
                userPref = ApiUrlBuilder.OPTION_FAVOURITES;
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        setActionBarTitle();
        loadMovieList();
        checkForResults();
        return true;
    }


    /**
     * Method to initialise the recycler view and set both layout manager and adapter
     */
    private void initRecyclerView() {
        setLayoutSpanCount();
        movieListLayoutManager = new GridLayoutManager(this, layoutSpanCount);
        movieListView.setLayoutManager(movieListLayoutManager);
        movieListView.setHasFixedSize(true);
        movieListAdapter = new MovieListAdapter(this);
        movieListView.setAdapter(movieListAdapter);
    }

    /**
     * Loads the list of movies
     */
    private void loadMovieList(){

        try{
            MovieDbApiInterface movieDbApiInterface =
                    ApiUrlBuilder.getRetrofitClient(this).create(MovieDbApiInterface.class);
            final List<Movie> movieListResults = new ArrayList<>();

            if (userPref.equals(ApiUrlBuilder.OPTION_FAVOURITES)) {
                Uri uri = FavouritesContract.FaveMovieEntry.CONTENT_URI;
                Cursor cursor = getContentResolver().query(uri, FAVE_MOVIE_ENTRY_COLUMNS, null, null, null);

                if (cursor != null) {
                    cursor.moveToFirst();
                    for (int i = 0; i < cursor.getCount(); i++) {
                        Movie movie = new Movie();
                        int tmdbID = cursor.getInt(cursor.getColumnIndex(FavouritesContract.FaveMovieEntry.TMDB_ID));
                        String posterURL = cursor.getString(cursor.getColumnIndex(FavouritesContract.FaveMovieEntry.POSTER_URL));
                        movie.setTmdbID(tmdbID);
                        movie.setPosterImageLink(posterURL);
                        movieListResults.add(movie);
                        cursor.moveToNext();
                    }
                    cursor.close();
                    movieListAdapter.setMovieList(movieListResults);
                }
            } else {
                for (int i = 1; i <= NUM_RESULTS_PAGES; i++) {
                    final Call<MovieJsonResponse> movieJsonResponseCall =
                            movieDbApiInterface.getMovieList(userPref, ApiUrlBuilder.API_KEY, i);

                    movieJsonResponseCall.enqueue(new Callback<MovieJsonResponse>() {
                        @Override
                        public void onResponse(Call<MovieJsonResponse> call, Response<MovieJsonResponse> response) {
                            if (response.isSuccessful()) {
                                movieListResults.addAll(response.body().getMovieList());
                                movieListAdapter.setMovieList(movieListResults);
                            }
                        }

                        @Override
                        public void onFailure(Call<MovieJsonResponse> call, Throwable t) {

                        }
                    });
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * A method to check the screen orientation and change the span count based on such.
     */
    private void setLayoutSpanCount(){
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            layoutSpanCount = PORTRAIT_SPAN_COUNT;
        }
        else{
            layoutSpanCount = LANDSCAPE_SPAN_COUNT;
        }
    }

    // Handling when there is no connection or no results, and display TextViews to tell the user.
    private void checkForResults() {
        if (!NetworkConnectionUtils.isConnected(this)) {
            movieListView.setVisibility(View.GONE);
            noConnectionView.setVisibility(View.VISIBLE);
            noResultsView.setVisibility(View.GONE);
            noFavouritesView.setVisibility(View.GONE);
        } else if (movieListAdapter.getItemCount() == 0) {

            movieListView.setVisibility(View.GONE);
            noConnectionView.setVisibility(View.GONE);

            if (userPref.equals(ApiUrlBuilder.OPTION_FAVOURITES)) {
                noFavouritesView.setVisibility(View.VISIBLE);
                noResultsView.setVisibility(View.GONE);
            } else {
                noResultsView.setVisibility(View.VISIBLE);
                noFavouritesView.setVisibility(View.GONE);
            }
        } else {
            movieListView.setVisibility(View.VISIBLE);
            noResultsView.setVisibility(View.GONE);
            noFavouritesView.setVisibility(View.GONE);
            noConnectionView.setVisibility(View.GONE);
        }
    }
}
