package com.coderman202.popularmovies;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.coderman202.popularmovies.adapters.MovieListAdapter;
import com.coderman202.popularmovies.builders.ApiUrlBuilder;
import com.coderman202.popularmovies.interfaces.MovieDbApiInterface;
import com.coderman202.popularmovies.model.Movie;
import com.coderman202.popularmovies.utilities.MovieJsonResponse;

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

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final String USER_PREF_KEY = "user_pref";

    public static final int PORTRAIT_SPAN_COUNT = 4;
    public static final int LANDSCAPE_SPAN_COUNT = 6;

    // All the UI elements
    @BindView(R.id.movie_list)
    RecyclerView movieListView;

    // Movie List Adapter
    MovieListAdapter movieListAdapter;

    // Layout Manager for Movie List Adapter
    GridLayoutManager movieListLayoutManager;

    // Number grid items to span the screen. To be set depending on the screen rotation
    int layoutSpanCount;

    String userPref = ApiUrlBuilder.OPTION_NOW_PLAYING;
    @BindString(R.string.now_playing) String actionBarPrefTitle;

    ActionBar actionBar;
    @BindString(R.string.app_name) String actionBarMainTitle;

    // To represent the full title for the Action Bar
    String actionBarFullTitle;

    // Setting the number of pages of results. Future versions may allow user to specify the number.
    int numResultsPages = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        actionBar = this.getSupportActionBar();

        if(savedInstanceState != null){
            userPref = savedInstanceState.getString(USER_PREF_KEY);
        }

        initRecyclerView();

        loadMovieList();

        setActionBarTitle();


    }

    // Handling screen rotations.
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString(USER_PREF_KEY, userPref);
    }

    // Handling screen rotations.
    @Override
    public void onRestoreInstanceState(Bundle inState){
        userPref = inState.getString(USER_PREF_KEY);
    }

    /**
     * Set the title depending on the list of movies as selected in the options menu by the user.
     */
    private void setActionBarTitle() {
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
                actionBarPrefTitle = getString(R.string.upcoming);
                break;
            case R.id.pop_option:
                userPref = ApiUrlBuilder.OPTION_POPULAR;
                actionBarPrefTitle = getString(R.string.popular);
                break;
            case R.id.now_playing_option:
                userPref = ApiUrlBuilder.OPTION_NOW_PLAYING;
                actionBarPrefTitle = getString(R.string.now_playing);
                break;
            case R.id.top_rated_option:
                userPref = ApiUrlBuilder.OPTION_TOP_RATED;
                actionBarPrefTitle = getString(R.string.top_rated);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        setActionBarTitle();
        loadMovieList();
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
            for (int i = 1; i <= numResultsPages; i++) {
                final Call<MovieJsonResponse> movieJsonResponseCall =
                        movieDbApiInterface.getMovieList(userPref, ApiUrlBuilder.API_KEY, i);

                movieJsonResponseCall.enqueue(new Callback<MovieJsonResponse>() {
                    @Override
                    public void onResponse(Call<MovieJsonResponse> call, Response<MovieJsonResponse> response) {
                        if(response.isSuccessful()){
                            movieListResults.addAll(response.body().getMovieList());
                            movieListAdapter.setMovieList(movieListResults);
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieJsonResponse> call, Throwable t) {

                    }
                });
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
}
