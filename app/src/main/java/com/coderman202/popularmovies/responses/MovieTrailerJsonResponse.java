package com.coderman202.popularmovies.responses;

import com.coderman202.popularmovies.model.MovieTrailer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Reggie on 21/03/2018.
 * Custom class which represents a list of movie trailer objects returned by the API call.
 */

public class MovieTrailerJsonResponse {
    @SerializedName("id")
    @Expose
    private int trailerListID;
    @SerializedName("results")
    @Expose
    private List<MovieTrailer> trailerList = null;

    public int getTrailerListID() {
        return trailerListID;
    }

    public void setTrailerListID(int trailerListID) {
        this.trailerListID = trailerListID;
    }

    public List<MovieTrailer> getTrailerList() {
        return trailerList;
    }

    public void setTrailerList(List<MovieTrailer> trailerList) {
        this.trailerList = trailerList;
    }

}
