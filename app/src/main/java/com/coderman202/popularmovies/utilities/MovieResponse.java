package com.coderman202.popularmovies.utilities;

import com.coderman202.popularmovies.model.Movie;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Reggie on 27/02/2018.
 * A class which represents the Movie object which are returned by the API call movie/{id}
 */
public class MovieResponse {

    @SerializedName("")
    private Movie movie;

    /**
     * Instantiates a new Movie response.
     */
    public MovieResponse() {
    }

    /**
     * Instantiates a new Movie response.
     *
     * @param movie the movie
     */
    public MovieResponse(Movie movie) {
        this.movie = movie;
    }

    /**
     * Gets movie.
     *
     * @return the movie
     */
    public Movie getMovie() {
        return movie;
    }

    /**
     * Sets movie.
     *
     * @param movie the movie
     */
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
