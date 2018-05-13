package com.coderman202.popularmovies.utilities;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.coderman202.popularmovies.builders.ApiUrlBuilder;

/**
 * Created by Reggie on 12/05/2018.
 * Custom utility class.
 */
public class PopularMoviesUtils {

    /**
     * Open youtube link. Check if the app exists, open in the app if it does. If not, then open
     * in the web.
     *
     * @param context   the context
     * @param youtubeID the youtube id
     */
    public static void openYoutubeLink(Context context, String youtubeID) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ApiUrlBuilder.YOUTUBE_TRAILER_APP_BASE_URL + youtubeID));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(ApiUrlBuilder.YOUTUBE_TRAILER_WEB_BASE_URL + youtubeID));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }
}
