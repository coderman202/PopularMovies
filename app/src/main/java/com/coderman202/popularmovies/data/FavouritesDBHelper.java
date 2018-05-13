package com.coderman202.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Reggie on 28/04/2018.
 * A custom DB Helper class to create, populate and update my db.
 */

public class FavouritesDBHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = FavouritesDBHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "FavouritesDB.db";
    private static final int DATABASE_VERSION = 1;

    private Context context;

    // Table names
    private static final String FAVOURITES_TABLE = FavouritesContract.FaveMovieEntry.TABLE_NAME;

    // Product table columns.
    private static final String FAVE_MOVIE_KEY = FavouritesContract.FaveMovieEntry.PK_FAVE_MOVIE_KEY;
    private static final String TMDB_ID = FavouritesContract.FaveMovieEntry.TMDB_ID;
    private static final String POSTER_URL = FavouritesContract.FaveMovieEntry.POSTER_URL;


    public FavouritesDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public Context getContext() {
        return context;
    }

    /**
     * A method to create tables in the db
     * @param db    the SQLite DB
     */
    public void createTables(SQLiteDatabase db){
        String query = "CREATE TABLE IF NOT EXISTS " + FAVOURITES_TABLE + "(" +
                FAVE_MOVIE_KEY + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                TMDB_ID + " INTEGER NOT NULL, " +
                POSTER_URL + " TEXT NOT NULL);";
        db.execSQL(query);
    }
}
