package com.coderman202.popularmovies.interfaces;

import com.coderman202.popularmovies.model.Movie;
import com.coderman202.popularmovies.utilities.MovieJsonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Reggie on 27/02/2018.
 * <p>
 * Setting up API interface for using retrofit.
 * Followed instructions at the following link:
 *
 * @see <a href = "https://www.youtube.com/watch?v=gGuUBlzmtPQ"</a>
 */
public interface MovieDbApiInterface {

    /**
     * Gets movie details for the movie relating to the specific ID on The Movie DB.
     *
     * @param tmdbID the tmdb id
     * @param apiKey the api key
     * @return {@link Movie} object
     */
    @GET("movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") int tmdbID, @Query("api_key") String apiKey);

    /**
     * Gets a list of movies sorted by the user preference.
     * Returned in the form of a MovieJsonResponse object which is equivalent to a list of movies.
     *
     * @param preference the preference
     * @param api_key    the api key
     * @return {@link MovieJsonResponse} object
     */
    @GET("movie/{preference}")
    Call<MovieJsonResponse> getMovieList(@Path("preference") String preference,
                                         @Query("api_key") String api_key);
}
