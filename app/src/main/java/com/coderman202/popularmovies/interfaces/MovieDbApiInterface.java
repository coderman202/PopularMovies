package com.coderman202.popularmovies.interfaces;

import com.coderman202.popularmovies.model.Movie;
import com.coderman202.popularmovies.responses.MovieJsonResponse;
import com.coderman202.popularmovies.responses.MovieReviewJsonResponse;
import com.coderman202.popularmovies.responses.MovieTrailerJsonResponse;

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
 * @see <a href="https://www.youtube.com/watch?v=gGuUBlzmtPQ">here</a>
 *
 * Also source documentation:
 * @see <a href="http://square.github.io/retrofit/">Retrofit</a>
 * @see <a href="http://square.github.io/okhttp/">OkHttp</a>
 */
public interface MovieDbApiInterface {

    /**
     * Gets movie details for the movie relating to the specific MOVIE_ID_KEY on The Movie DB.
     *
     * @param id the tmdb id
     * @param api_key the api key
     * @return {@link Movie} object
     */
    @GET("movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") int id, @Query("api_key") String api_key);

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
                                         @Query("api_key") String api_key,
                                         @Query("page") int pageNum);

    /**
     * Gets a list of movie trailers  for the particular movie id.
     * Returned in the form of a MovieTrailerJsonResponse object which is equivalent to a list of
     * movie trailers.
     *
     * @param id the tmbd id
     * @param api_key    the api key
     * @return {@link MovieTrailerJsonResponse} object
     */
    @GET("movie/{id}/videos")
    Call<MovieTrailerJsonResponse> getMovieTrailerList(@Path("id") int id,
                                                @Query("api_key") String api_key);


    /**
     * Gets a list of movie reviews  for the particular movie id.
     * Returned in the form of a MovieReviewJsonResponse object which is equivalent to a list of
     * movie reviews.
     *
     * @param id the tmbd id
     * @param api_key    the api key
     * @return {@link MovieReviewJsonResponse} object
     */
    @GET("movie/{id}/reviews")
    Call<MovieReviewJsonResponse> getMovieReviewList(@Path("id") int id,
                                                      @Query("api_key") String api_key);
}
