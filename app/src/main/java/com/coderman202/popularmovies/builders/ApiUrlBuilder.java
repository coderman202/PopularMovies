package com.coderman202.popularmovies.builders;

import android.content.Context;

import com.coderman202.popularmovies.BuildConfig;
import com.coderman202.popularmovies.utilities.NetworkConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Reggie on 28/02/2018.
 * Builds URL with base URL constant and gets network connectivity to TMDB API.
 * Uses Retrofit with OkHttp.
 * This was one of my resources:
 * @see <a href="https://code.tutsplus.com/tutorials/getting-started-with-retrofit-2--cms-27792">
 *     Getting Started with Retrofit 2 HTTP Client</a>
 * User: Chike Mgbemena
 * Date: 2016/12/16
 *
 * Further resources are referenced in:
 * {@link com.coderman202.popularmovies.interfaces.MovieDbApiInterface}
 *
 */

public class ApiUrlBuilder {

    private static Retrofit retrofitClient = null;

    // ENTER YOUR OWN API KEY HERE
    public static final String API_KEY = BuildConfig.API_KEY;

    // Constant - how long til connection will timeout - in milliseconds.
    public static final int CONNECT_TIMEOUT = 15000;

    // User movie list sort options
    public static final String OPTION_NOW_PLAYING = "now_playing";
    public static final String OPTION_UPCOMING = "upcoming";
    public static final String OPTION_POPULAR = "popular";
    public static final String OPTION_TOP_RATED = "top_rated";

    // Base URLs
    public static final String TMDB_BASE_URL = "http://api.themoviedb.org/3/";
    public static final String TMDB_BASE_LINK_URL = "https://www.themoviedb.org/movie/";
    public static final String IMDB_BASE_LINK_URL = "https://www.imdb.com/title/";
    public final static String MOVIE_POSTER_PATH_BASE_URL = "http://image.tmdb.org/t/p/w200";
    public final static String BACKDROP_POSTER_PATH_BASE_URL = "http://image.tmdb.org/t/p/w500";
    public final static String YOUTUBE_IMG_BASE_URL = "https://img.youtube.com/vi/";
    public final static String YOUTUBE_IMG_HQ = "hqdefault.jpg";
    public final static String YOUTUBE_IMG_MQ = "mqdefault.jpg";
    public final static String YOUTUBE_IMG_SD= "sddefault.jpg";


    public static Retrofit getRetrofitClient(Context context) throws IOException{

        if(!NetworkConnectionUtils.isConnected(context)){
            throw new IOException();
        }

        if(retrofitClient == null){
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

            builder.addInterceptor(interceptor);
            builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);

            retrofitClient = new Retrofit.Builder().baseUrl(TMDB_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(builder.build()).build();
        }

        return retrofitClient;

    }


}
