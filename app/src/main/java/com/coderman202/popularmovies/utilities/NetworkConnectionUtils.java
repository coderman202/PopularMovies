package com.coderman202.popularmovies.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Reggie on 25/02/2018.
 * Class to store base URLs and API keys
 */

public class NetworkConnectionUtils {

    /**
     * Method to check network connectivity
     * @param context The context
     * @return TRUE is connected or else FALSE
     */
    public static boolean isConnected(Context context) {
        final ConnectivityManager manager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }
}