package com.coderman202.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.coderman202.popularmovies.R;

/**
 * Created by Reggie on 28/04/2018.
 * A custom provider class to interact with the DB of user favourites
 */

public class FavouritesProvider extends ContentProvider {

    private static final String LOG_TAG = FavouritesProvider.class.getSimpleName();

    // FAVOURITES TABLE CONSTANTS
    private static final int FAVOURITES_TABLE = 100;
    private static final int FAVOURITES_TABLE_ROW = 101;
    private static final String PATH_FAVOURITES = FavouritesContract.FaveMovieEntry.PATH;
    private static final String FAVOURITES_TABLE_NAME = FavouritesContract.FaveMovieEntry.TABLE_NAME;
    private static final String FAVOURITES_TABLE_LIST_TYPE = FavouritesContract.FaveMovieEntry.CONTENT_LIST_TYPE;
    private static final String FAVOURITES_TABLE_ITEM_TYPE = FavouritesContract.FaveMovieEntry.CONTENT_ITEM_TYPE;

    // Columns
    private static final String FAVE_MOVIE_KEY = FavouritesContract.FaveMovieEntry.PK_FAVE_MOVIE_KEY;
    private static final String TMDB_ID = FavouritesContract.FaveMovieEntry.TMDB_ID;
    private static final String POSTER_URL = FavouritesContract.FaveMovieEntry.POSTER_URL;


    // DB helper object for the Favourites DB
    private FavouritesDBHelper dbHelper;

    public static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // URIs for the Favourites table
        uriMatcher.addURI(FavouritesContract.CONTENT_AUTHORITY, PATH_FAVOURITES, FAVOURITES_TABLE);
        uriMatcher.addURI(FavouritesContract.CONTENT_AUTHORITY, PATH_FAVOURITES + "/#", FAVOURITES_TABLE_ROW);

    }

    /**
     * Initialize the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {
        dbHelper = new FavouritesDBHelper(getContext());
        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection,
     * selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // This cursor will hold query result.
        Cursor cursor;

        // Making sure the URI is valid
        final int match = uriMatcher.match(uri);
        switch(match){
            case FAVOURITES_TABLE:
                cursor = db.query(FAVOURITES_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case FAVOURITES_TABLE_ROW:
                selection = FavouritesContract.FaveMovieEntry.PK_FAVE_MOVIE_KEY + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(FAVOURITES_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_unsupported_insertion_exception, uri));
        }
        return cursor;
    }

    //region Data insertion methods
    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        // Making sure the URI is valid
        final int match = uriMatcher.match(uri);
        switch (match){
            case FAVOURITES_TABLE:
                return insertFavourite(uri, values);
            default:
                throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_unsupported_insertion_exception, uri));
        }
    }

    /**
     * A method to ensure all the values for favourite movies to be inserted are correct and valid.
     * @param uri       the uri
     * @param values    the values to be inserted for each column
     * @return          the uri including if all values are valid, otherwise null
     */
    public Uri insertFavourite(Uri uri, ContentValues values){

        // Check the TMDB ID is not null
        Integer movie_id = values.getAsInteger(TMDB_ID);
        if (movie_id == null) {
            throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_tmdb_id_exception));
        }

        // Check the Poster URL is not null too.
        String url = values.getAsString(POSTER_URL);
        if(url == null || url.equals("") || url.length() == 0){
            throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_poster_url_exception));
        }

        // Get writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Insert the new favourite with the given values
        long id = db.insert(FAVOURITES_TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, dbHelper.getContext().getString(R.string.content_uri_insert_failure_exception, uri));
            return null;
        }

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }
    //endregion

    //region Update database methods
    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        final int match = uriMatcher.match(uri);
        switch (match) {
            case FAVOURITES_TABLE:
                return updateFavourite(uri, contentValues, selection, selectionArgs);
            case FAVOURITES_TABLE_ROW:

                selection = FAVE_MOVIE_KEY + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateFavourite(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_unsupported_update_exception, uri));
        }
    }

    /**
     * A method which validates the update of a favourite or number of favourites in the db.
     * @param uri               the uri of the favourite entry
     * @param values            the content values
     * @param selection         selection
     * @param selectionArgs     selection arguments
     * @return                  Returns the number of database rows affected by the update statement
     */
    private int updateFavourite(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        // If the {@link FaveMovieEntry#TMDB_ID} key is present,
        // check that the name value is not null.
        if (values.containsKey(TMDB_ID)) {
            Integer movie_id = values.getAsInteger(TMDB_ID);
            if (movie_id == null) {
                throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_tmdb_id_exception));
            }
        }

        // If the {@link FaveMovieEntry#POSTER_URL} key is present,
        // check that the price value is valid.
        if (values.containsKey(POSTER_URL)) {
            String url = values.getAsString(POSTER_URL);
            if(url == null || url.equals("") || url.length() == 0){
                throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_poster_url_exception));
            }
        }

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writable database to update the data
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        return database.update(FAVOURITES_TABLE_NAME, values, selection, selectionArgs);
    }
    //endregion

    //region Delete method(s)
    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        final int match = uriMatcher.match(uri);
        switch(match){
            case FAVOURITES_TABLE:
                // Delete all rows that match the selection and selection args
                return db.delete(FAVOURITES_TABLE_NAME, selection, selectionArgs);
            case FAVOURITES_TABLE_ROW:
                // Delete a single row given by the ID in the URI
                selection = FAVE_MOVIE_KEY + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return db.delete(FAVOURITES_TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_unsupported_deletion_exception, uri));
        }
    }
    //endregion

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case FAVOURITES_TABLE:
                return FAVOURITES_TABLE_LIST_TYPE;
            case FAVOURITES_TABLE_ROW:
                return FAVOURITES_TABLE_ITEM_TYPE;
            default:
                throw new IllegalStateException(dbHelper.getContext().getString(R.string.content_uri_unknown_exception, uri, match));
        }
    }
}
