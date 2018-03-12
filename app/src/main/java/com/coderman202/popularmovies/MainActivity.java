package com.coderman202.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.coderman202.popularmovies.adapters.MovieListAdapter;
import com.coderman202.popularmovies.builders.ApiUrlBuilder;
import com.coderman202.popularmovies.interfaces.MovieDbApiInterface;
import com.coderman202.popularmovies.model.Movie;
import com.coderman202.popularmovies.utilities.MovieJsonResponse;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    // All the UI elements
    @BindView(R.id.movie_list)
    RecyclerView movieListView;

    // Movie List Adapter
    MovieListAdapter movieListAdapter;

    // Layout Manager for Movie List Adapter
    GridLayoutManager movieListLayoutManager;

    // JSON response object to be called to create the movie list.
    MovieJsonResponse movieJsonResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


        initRecyclerView();

        loadMovieList();


    }

    // Method to initialise the recycler view and set both layout manager and adapter
    private void initRecyclerView() {
        movieListLayoutManager = new GridLayoutManager(this, 4);
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
            final Call<MovieJsonResponse> movieJsonResponseCall =
                    movieDbApiInterface.getMovieList("now_playing", ApiUrlBuilder.API_KEY);

            movieJsonResponseCall.enqueue(new Callback<MovieJsonResponse>() {
                @Override
                public void onResponse(Call<MovieJsonResponse> call, Response<MovieJsonResponse> response) {
                    int responseCode = response.code();
                    if(response.isSuccessful()){
                        List<Movie> movieList = response.body().getMovieList();
                        movieListAdapter.setMovieList(movieList);
                    }
                }

                @Override
                public void onFailure(Call<MovieJsonResponse> call, Throwable t) {

                }
            });

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
