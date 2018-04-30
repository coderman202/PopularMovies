package com.coderman202.popularmovies.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Reggie on 28/04/2018.
 * A custom contract class to store the information about the tables in the db
 */

public class FavouritesContract {

    // The content authority and the base content URI which will be used to generate all URIs in
    // this contract class.
    public static final String CONTENT_AUTHORITY = "com.example.android.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Empty private constructor. Ensures class is not going to be initialised.
    private FavouritesContract(){}

    public static final class FaveMovieEntry implements BaseColumns {

        // Table name
        public static final String TABLE_NAME = "Favourites";

        // Table path for URI
        public static final String PATH = TABLE_NAME.toLowerCase();

        // Content URI
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of favourite movies.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single movie.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;


        // Primary key
        public static final String PK_FAVE_MOVIE_KEY = BaseColumns._ID;

        // Other column names
        public static final String TMDB_ID = "TMDB_ID";
        public static final String POSTER_URL = "PosterUrl";
    }
}
