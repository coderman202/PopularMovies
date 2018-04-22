package com.coderman202.popularmovies.responses;

import com.coderman202.popularmovies.model.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Reggie on 27/02/2018.
 * A class which represents lists of Movie objects which are returned by the API
 */
public class MovieJsonResponse {

    @SerializedName("total_results")
    private int resultsCount;

    @SerializedName("total_pages")
    private int pageTotal;

    @SerializedName("page")
    private int pageNumber;

    @SerializedName("results")
    private List<Movie> movieList;


    /**
     * Instantiates a new Movie json response.
     *
     * @param resultsCount the results count
     * @param pageTotal    the page total
     * @param pageNumber   the page number
     * @param movieList    the movie list
     */
    public MovieJsonResponse(int resultsCount, int pageTotal, int pageNumber,
                             List<Movie> movieList) {
        this.resultsCount = resultsCount;
        this.pageTotal = pageTotal;
        this.pageNumber = pageNumber;
        this.movieList = movieList;
    }

    /**
     * Instantiates a new Movie json response.
     * Empty constructor
     */
    public MovieJsonResponse() {
    }


    /**
     * Gets results count.
     *
     * @return the results count
     */
    public int getResultsCount() {
        return resultsCount;
    }

    /**
     * Sets results count.
     *
     * @param resultsCount the results count
     */
    public void setResultsCount(int resultsCount) {
        this.resultsCount = resultsCount;
    }

    /**
     * Gets page total.
     *
     * @return the page total
     */
    public int getPageTotal() {
        return pageTotal;
    }

    /**
     * Sets page total.
     *
     * @param pageTotal the page total
     */
    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    /**
     * Gets page number.
     *
     * @return the page number
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Sets page number.
     *
     * @param pageNumber the page number
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * Gets movie list.
     *
     * @return the movie list
     */
    public List<Movie> getMovieList() {
        return movieList;
    }

    /**
     * Sets movie list.
     *
     * @param movieList the movie list
     */
    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }
}
